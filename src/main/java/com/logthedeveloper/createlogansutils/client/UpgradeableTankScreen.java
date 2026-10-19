package com.logthedeveloper.createlogansutils.client;

import java.util.ArrayList;
import java.util.List;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.menu.UpgradeableTankMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class UpgradeableTankScreen extends AbstractContainerScreen<UpgradeableTankMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CreateLogansUtils.MODID, "textures/gui/upgradeable_tank.png");


    private static final int GAUGE_X = 26;
    private static final int GAUGE_Y = 17;
    private static final int GAUGE_W = 16;
    private static final int GAUGE_H = 52;

    public UpgradeableTankScreen(UpgradeableTankMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = 72;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        FluidTank tank = menu.be.getTank();
        FluidStack fluid = tank.getFluid();
        if (fluid.isEmpty()) return;

        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(ext.getStillTexture(fluid));
        int tint = ext.getTintColor(fluid);

        int height = menu.be.isInfinite()
                ? GAUGE_H
                : (int) ((long) GAUGE_H * fluid.getAmount() / Math.max(1, tank.getCapacity()));
        height = Math.max(1, Math.min(GAUGE_H, height));

        int x = leftPos + GAUGE_X;
        int yBottom = topPos + GAUGE_Y + GAUGE_H;

        graphics.enableScissor(x, yBottom - height, x + GAUGE_W, yBottom);
        graphics.setColor(
                (tint >> 16 & 255) / 255f,
                (tint >> 8 & 255) / 255f,
                (tint & 255) / 255f,
                (tint >>> 24 & 255) / 255f);
        for (int y = yBottom - 16; y > yBottom - height - 16; y -= 16) {
            graphics.blit(x, y, 0, 16, 16, sprite);
        }
        graphics.setColor(1f, 1f, 1f, 1f);
        graphics.disableScissor();
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        if (isHovering(GAUGE_X, GAUGE_Y, GAUGE_W, GAUGE_H, mouseX, mouseY)) {
            FluidTank tank = menu.be.getTank();
            String capacity = menu.be.isInfinite() ? "\u221E" : String.format("%,d", tank.getCapacity());

            List<Component> lines = new ArrayList<>();
            lines.add(tank.isEmpty() ? Component.literal("Empty") : tank.getFluid().getHoverName());
            lines.add(Component.literal(String.format("%,d / %s mB", tank.getFluidAmount(), capacity))
                    .withStyle(ChatFormatting.GRAY));
            graphics.renderComponentTooltip(font, lines, mouseX, mouseY);
        }
    }
}