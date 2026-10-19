package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.block.entity.UpgradeableTankBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

// Draws the stored fluid inside the tank's window cavity, scaled to the fill level.
public class UpgradeableTankRenderer implements BlockEntityRenderer<UpgradeableTankBlockEntity> {

    // The cavity inside your model: walls are 1px thick, floor top is y=4, lid bottom is y=12.
    // Everything is inset by 0.02px so the fluid never sits exactly on top of model faces (z-fighting).
    private static final float X0 = 1.02f / 16f, X1 = 14.98f / 16f;
    private static final float Z0 = 1.02f / 16f, Z1 = 14.98f / 16f;
    private static final float Y0 = 4.02f / 16f, Y1 = 11.98f / 16f;

    // So a tank with a tiny amount of fluid still shows a sliver instead of nothing
    private static final float MIN_HEIGHT = 1f / 16f;

    public UpgradeableTankRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(UpgradeableTankBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        FluidTank tank = be.getTank();
        FluidStack fluid = tank.getFluid();
        if (fluid.isEmpty() || tank.getCapacity() <= 0) return;

        float fill = be.isInfinite()
                ? 1f
                : Math.min(1f, (float) ((double) fluid.getAmount() / tank.getCapacity()));
        float yTop = Math.min(Y1, Y0 + Math.max(MIN_HEIGHT, (Y1 - Y0) * fill));
        float height = yTop - Y0;
        float width = X1 - X0;

        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(ext.getStillTexture(fluid));

        int tint = ext.getTintColor(fluid);
        float a = ((tint >> 24) & 0xFF) / 255f;
        if (a <= 0f) a = 1f; // some fluids (lava) have no alpha set
        float r = ((tint >> 16) & 0xFF) / 255f;
        float g = ((tint >> 8) & 0xFF) / 255f;
        float b = (tint & 0xFF) / 255f;

        // Glowing fluids (lava etc.) render fully bright
        int light = fluid.getFluidType().getLightLevel(fluid) > 10 ? LightTexture.FULL_BRIGHT : packedLight;

        float u0 = sprite.getU0(), u1 = sprite.getU1();
        float v0 = sprite.getV0(), v1 = sprite.getV1();
        float uEnd = u0 + (u1 - u0) * width;
        float vSideTop = v1 - (v1 - v0) * height;   // side faces: bottom of sprite at the bottom of the fluid
        float vFlatEnd = v0 + (v1 - v0) * width;    // top face

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.translucent());
        PoseStack.Pose pose = poseStack.last();

        // North
        face(consumer, pose, r, g, b, a, light, 0, 0, -1,
                u0, vSideTop, uEnd, v1,
                X0, Y0, Z0, X1, Y0, Z0, X1, yTop, Z0, X0, yTop, Z0);
        // South
        face(consumer, pose, r, g, b, a, light, 0, 0, 1,
                u0, vSideTop, uEnd, v1,
                X1, Y0, Z1, X0, Y0, Z1, X0, yTop, Z1, X1, yTop, Z1);
        // East
        face(consumer, pose, r, g, b, a, light, 1, 0, 0,
                u0, vSideTop, uEnd, v1,
                X1, Y0, Z0, X1, Y0, Z1, X1, yTop, Z1, X1, yTop, Z0);
        // West
        face(consumer, pose, r, g, b, a, light, -1, 0, 0,
                u0, vSideTop, uEnd, v1,
                X0, Y0, Z1, X0, Y0, Z0, X0, yTop, Z0, X0, yTop, Z1);
        // Top surface
        face(consumer, pose, r, g, b, a, light, 0, 1, 0,
                u0, v0, uEnd, vFlatEnd,
                X0, yTop, Z1, X1, yTop, Z1, X1, yTop, Z0, X0, yTop, Z0);
    }

    /**
     * Draws one quad twice (front and back) so it's visible from either side.
     * Vertices go bottom-left, bottom-right, top-right, top-left.
     * uA/vA = UV of the top edge, uB/vB = UV of the bottom edge.
     */
    private static void face(VertexConsumer c, PoseStack.Pose pose,
                             float r, float g, float b, float a, int light,
                             float nx, float ny, float nz,
                             float uA, float vA, float uB, float vB,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float x3, float y3, float z3,
                             float x4, float y4, float z4) {
        // front
        vertex(c, pose, x1, y1, z1, uA, vB, r, g, b, a, light, nx, ny, nz);
        vertex(c, pose, x2, y2, z2, uB, vB, r, g, b, a, light, nx, ny, nz);
        vertex(c, pose, x3, y3, z3, uB, vA, r, g, b, a, light, nx, ny, nz);
        vertex(c, pose, x4, y4, z4, uA, vA, r, g, b, a, light, nx, ny, nz);
        // back (reversed winding)
        vertex(c, pose, x4, y4, z4, uA, vA, r, g, b, a, light, -nx, -ny, -nz);
        vertex(c, pose, x3, y3, z3, uB, vA, r, g, b, a, light, -nx, -ny, -nz);
        vertex(c, pose, x2, y2, z2, uB, vB, r, g, b, a, light, -nx, -ny, -nz);
        vertex(c, pose, x1, y1, z1, uA, vB, r, g, b, a, light, -nx, -ny, -nz);
    }

    private static void vertex(VertexConsumer c, PoseStack.Pose pose, float x, float y, float z,
                               float u, float v, float r, float g, float b, float a, int light,
                               float nx, float ny, float nz) {
        c.addVertex(pose, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
    }
}