package com.logthedeveloper.createlogansutils;

import com.logthedeveloper.createlogansutils.item.*;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.logthedeveloper.createlogansutils.recipe.ModRecipes;
import com.logthedeveloper.createlogansutils.fluid.ModFluidType;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemLore;
import com.logthedeveloper.createlogansutils.fluid.ModFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CreateLogansUtils.MODID)
public class CreateLogansUtils {

    public static final String MODID = "createlogansutils";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static final DeferredItem<BlockItem> YOUNG_CHEESE_BIN =
            ITEMS.register("young_cheese_bin", () ->
                    new BlockItem(ModBlocks.YOUNG_CHEESE_BIN.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> AGED_CHEESE_BIN =
            ITEMS.register("aged_cheese_bin", () ->
                    new BlockItem(ModBlocks.AGED_CHEESE_BIN.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> BLOCK_OF_CHEESE =
            ITEMS.register("block_of_cheese", () ->
                    new BlockItem(ModBlocks.BLOCK_OF_CHEESE.get(), new Item.Properties()));

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_BARKFART_SOUND =
            SOUND_EVENTS.register("music_disc.barkfart",
                    () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "music_disc.barkfart"), 16.0f));
    public static final DeferredItem<MusicDiscItem> MUSIC_DISC_BARKFART = ITEMS.registerItem("music_disc_barkfart",
            properties -> new MusicDiscItem(properties
                    .jukeboxPlayable(
                            ResourceKey.create(Registries.JUKEBOX_SONG,
                                    ResourceLocation.fromNamespaceAndPath(MODID, "barkfart"))
                    )
                    .stacksTo(1)
                    .component(DataComponents.LORE, new ItemLore(java.util.List.of(
                            Component.literal("SØLZÉ arr. logtheinsane")
                    )))
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_GOLDENBROWN_SOUND =
            SOUND_EVENTS.register("music_disc.goldenbrown",
                    () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "music_disc.goldenbrown"), 16.0f));
    public static final DeferredItem<MusicDiscItemGB> MUSIC_DISC_GOLDENBROWN = ITEMS.registerItem("music_disc_goldenbrown",
            properties -> new MusicDiscItemGB(properties
                    .jukeboxPlayable(
                            ResourceKey.create(Registries.JUKEBOX_SONG,
                                    ResourceLocation.fromNamespaceAndPath(MODID, "goldenbrown"))
                    )
                    .stacksTo(1)
                    .component(DataComponents.LORE, new ItemLore(java.util.List.of(
                            Component.literal("The Stragglers")
                    )))
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_AUREASOL_SOUND =
            SOUND_EVENTS.register("music_disc.aureasol",
                    () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "music_disc.aureasol"), 16.0f));
    public static final DeferredItem<MusicDiscItemGB> MUSIC_DISC_AUREASOL = ITEMS.registerItem("music_disc_aureasol",
            properties -> new MusicDiscItemGB(properties
                    .jukeboxPlayable(
                            ResourceKey.create(Registries.JUKEBOX_SONG,
                                    ResourceLocation.fromNamespaceAndPath(MODID, "aureasol"))
                    )
                    .stacksTo(1)
                    .component(DataComponents.LORE, new ItemLore(java.util.List.of(
                            Component.literal("Delta")
                    )))
            ));
    public static final DeferredItem<Item> SULFUR_DUST = ITEMS.registerSimpleItem("sulfur_dust");
    public static final DeferredItem<CheesyBreadItem> CHEESY_BREAD = ITEMS.registerItem("cheesy_bread",
            CheesyBreadItem::new,
            new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(0.1f)
                            .build()
            ));
    public static final DeferredHolder<Item, Item> CHEESE_SLICE = ITEMS.register(
            "cheese_slice",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2) // Hunger points (1 hunger shank = 2 nutrition points)
                            .saturationModifier(0.5f) // Saturation calculation
                            .build()
                    )
            )
    );
    public static final DeferredItem<FeeshItem> FEESH = ITEMS.registerItem("feesh",
            FeeshItem::new,
            new Item.Properties().attributes(FeeshItem.createFeeshAttributes()));
    public static final DeferredItem<Item> CHEESE_BOWL = ITEMS.registerSimpleItem("cheese_bowl");
    public static final DeferredItem<BlazeMilkCakeItem> BLAZE_MILK_CAKE = ITEMS.registerItem("blaze_milk_cake",
            BlazeMilkCakeItem::new,
            new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.8f)
                            .build()
            ));


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_MORE_CAKES_TAB = CREATIVE_MODE_TABS.register("createlogansutilstab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createlogansutils"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BLAZE_MILK_CAKE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BLAZE_MILK_CAKE.get());
                output.accept(SULFUR_DUST.get());
                output.accept(CHEESE_BOWL.get());
                output.accept(CHEESY_BREAD.get());
                output.accept(CHEESE_SLICE.get());
                output.accept(FEESH.get());
                output.accept(MUSIC_DISC_BARKFART.get());
                output.accept(YOUNG_CHEESE_BIN.get());
                output.accept(AGED_CHEESE_BIN.get());
                output.accept(BLOCK_OF_CHEESE.get());
                output.accept(MUSIC_DISC_GOLDENBROWN.get());
                output.accept(MUSIC_DISC_AUREASOL.get());
                output.accept(ModFluids.LIQUID_REDSTONE_BUCKET.get());
            }).build());

    public CreateLogansUtils(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeMod.enableMilkFluid();

        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModFluids.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // creative thing idk
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("if you see this, idk");
    }
}
