package com.logthedeveloper.createlogansutils;

import com.logthedeveloper.createlogansutils.fluid.ModFluidClientExtensions;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CreateLogansUtils.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CreateLogansUtils.MODID, value = Dist.CLIENT)
public class CreateLogansUtilsClient {

    public CreateLogansUtilsClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(ModFluidClientExtensions::registerClientExtensions);

        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                ConfigurationScreen::new
        );
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        CreateLogansUtils.LOGGER.info("HELLO FROM CLIENT SETUP");
        CreateLogansUtils.LOGGER.info(
                "MINECRAFT NAME >> {}",
                Minecraft.getInstance().getUser().getName()
        );
    }
}