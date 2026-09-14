package com.logthedeveloper.createlogansutils.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MusicDiscItemGB extends Item {
    public MusicDiscItemGB(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(ChatFormatting.DARK_GREEN);
    }
}