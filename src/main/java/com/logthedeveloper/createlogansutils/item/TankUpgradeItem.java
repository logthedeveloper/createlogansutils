package com.logthedeveloper.createlogansutils.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class TankUpgradeItem extends Item {
    public static final int INFINITE = -1;

    private final int multiplier;

    public TankUpgradeItem(Properties props, int multiplier) {
        super(props.stacksTo(16));
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean isInfinite() {
        return multiplier == INFINITE;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(isInfinite() ? "Infinite fluid capacity" : multiplier + "x fluid capacity")
                .withStyle(ChatFormatting.GRAY));
    }
}