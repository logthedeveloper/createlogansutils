package com.logthedeveloper.createlogansutils.effect;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID)
public class RedstonedAttackHandler {

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.hasEffect(ModMobEffects.REDSTONED)) {
            event.setCanceled(true);
            player.displayClientMessage(Component.literal("You're too dazed to fight..."), true);
        }
    }
    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        Player playere = event.getPlayer();
        if (event.getPlayer() != null && event.getPlayer().hasEffect(ModMobEffects.REDSTONED)) {
            event.setCanceled(true);
            playere.displayClientMessage(Component.literal("You're too dazed to fight..."), true);
        }
    }
}