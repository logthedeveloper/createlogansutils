package com.logthedeveloper.createlogansutils;

import com.logthedeveloper.createlogansutils.item.*;
import com.logthedeveloper.createlogansutils.worldgen.ModTerraBlender;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.logthedeveloper.createlogansutils.recipe.ModRecipes;
import com.logthedeveloper.createlogansutils.fluid.ModFluidType;
import net.minecraft.sounds.SoundEvent;
import com.logthedeveloper.createlogansutils.recipe.ModJuicerRecipes;
import com.logthedeveloper.createlogansutils.client.ModPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import com.logthedeveloper.createlogansutils.effect.ModMobEffects;
import com.logthedeveloper.createlogansutils.attachment.ModAttachments;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import com.logthedeveloper.createlogansutils.block.entity.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
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
import com.logthedeveloper.createlogansutils.datagen.DataGenerators;
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
    public static final DeferredHolder<SoundEvent, SoundEvent> JUICER_HIT_SOUND =
            SOUND_EVENTS.register("mechanical_juicer_hit", () ->
                    SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(MODID, "mechanical_juicer_hit")));
    public static final DeferredItem<BlockItem> YOUNG_CHEESE_BIN =
            ITEMS.register("young_cheese_bin", () ->
                    new BlockItem(ModBlocks.YOUNG_CHEESE_BIN.get(), new Item.Properties()));

    public static final DeferredItem<BlockItem> AGED_CHEESE_BIN =
            ITEMS.register("aged_cheese_bin", () ->
                    new BlockItem(ModBlocks.AGED_CHEESE_BIN.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MECHANICAL_JUICER =
            ITEMS.register("mechanical_juicer", () ->
                    new BlockItem(ModBlocks.MECHANICAL_JUICER.get(), new Item.Properties()));
    public class ModWoodTypes {
        public static final BlockSetType REDSTONE_SET =
                BlockSetType.register(new BlockSetType("createlogansutils_redstone"));
        public static final WoodType REDSTONE =
                WoodType.register(new WoodType("createlogansutils:redstone", REDSTONE_SET));
    }
// output.accept(MECHANICAL_JUICER.get());


    public static final DeferredItem<BlockItem> BLOCK_OF_CHEESE =
            ITEMS.register("block_of_cheese", () ->
                    new BlockItem(ModBlocks.BLOCK_OF_CHEESE.get(), new Item.Properties()));
    public static final DeferredItem<ItemNameBlockItem> MINT_SEEDS =
            ITEMS.register("mint_seeds", () ->
                    new ItemNameBlockItem(ModBlocks.MINT_CROP.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> MINT = ITEMS.register("mint",
            () -> new BlockItem(ModBlocks.MINT.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> REDSTONE_DIRT_ITEM = ITEMS.registerSimpleBlockItem("redstone_dirt", ModBlocks.REDSTONE_DIRT);
    public static final DeferredItem<BlockItem> REDSTONE_GRASS_ITEM = ITEMS.registerSimpleBlockItem("redstone_grass", ModBlocks.REDSTONE_GRASS);
    public static final DeferredItem<BlockItem> REDSTONE_LOG_ITEM = ITEMS.registerSimpleBlockItem("redstone_log", ModBlocks.REDSTONE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_REDSTONE_LOG_ITEM = ITEMS.registerSimpleBlockItem("stripped_redstone_log", ModBlocks.STRIPPED_REDSTONE_LOG);
    public static final DeferredItem<BlockItem> REDSTONE_WOOD_ITEM = ITEMS.registerSimpleBlockItem("redstone_wood", ModBlocks.REDSTONE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_REDSTONE_WOOD_ITEM = ITEMS.registerSimpleBlockItem("stripped_redstone_wood", ModBlocks.STRIPPED_REDSTONE_WOOD);
    public static final DeferredItem<BlockItem> REDSTONE_PLANKS_ITEM = ITEMS.registerSimpleBlockItem("redstone_planks", ModBlocks.REDSTONE_PLANKS);
    public static final DeferredItem<BlockItem> REDSTONE_STAIRS_ITEM = ITEMS.registerSimpleBlockItem("redstone_stairs", ModBlocks.REDSTONE_STAIRS);
    public static final DeferredItem<BlockItem> REDSTONE_SLAB_ITEM = ITEMS.registerSimpleBlockItem("redstone_slab", ModBlocks.REDSTONE_SLAB);
    public static final DeferredItem<BlockItem> REDSTONE_FENCE_ITEM = ITEMS.registerSimpleBlockItem("redstone_fence", ModBlocks.REDSTONE_FENCE);
    public static final DeferredItem<BlockItem> REDSTONE_FENCE_GATE_ITEM = ITEMS.registerSimpleBlockItem("redstone_fence_gate", ModBlocks.REDSTONE_FENCE_GATE);
    public static final DeferredItem<BlockItem> REDSTONE_DOOR_ITEM = ITEMS.registerSimpleBlockItem("redstone_door", ModBlocks.REDSTONE_DOOR);
    public static final DeferredItem<BlockItem> REDSTONE_TRAPDOOR_ITEM = ITEMS.registerSimpleBlockItem("redstone_trapdoor", ModBlocks.REDSTONE_TRAPDOOR);
    public static final DeferredItem<BlockItem> REDSTONE_BUTTON_ITEM = ITEMS.registerSimpleBlockItem("redstone_button", ModBlocks.REDSTONE_BUTTON);
    public static final DeferredItem<BlockItem> REDSTONE_PRESSURE_PLATE_ITEM = ITEMS.registerSimpleBlockItem("redstone_pressure_plate", ModBlocks.REDSTONE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> REDSTONE_LEAVES_ITEM = ITEMS.registerSimpleBlockItem("redstone_leaves", ModBlocks.REDSTONE_LEAVES);
    public static final DeferredItem<BlockItem> REDSTONE_SAPLING_ITEM = ITEMS.registerSimpleBlockItem("redstone_sapling", ModBlocks.REDSTONE_SAPLING);

    public static final DeferredItem<SignItem> REDSTONE_SIGN_ITEM = ITEMS.register("redstone_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16),
                    ModBlocks.REDSTONE_SIGN.get(), ModBlocks.REDSTONE_WALL_SIGN.get()));
    public static final DeferredItem<HangingSignItem> REDSTONE_HANGING_SIGN_ITEM = ITEMS.register("redstone_hanging_sign",
            () -> new HangingSignItem(ModBlocks.REDSTONE_HANGING_SIGN.get(),
                    ModBlocks.REDSTONE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

   /*  public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_DISC_BARKFART_SOUND =
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
            )); */
    public static final DeferredItem<Item> SULFUR_DUST = ITEMS.registerSimpleItem("sulfur_dust");
    public static final DeferredItem<Item> BASKET = ITEMS.registerSimpleItem("basket");
    public static final DeferredItem<Item> LAVA_COMPONENT_1 = ITEMS.registerSimpleItem("lava_component_1");
    public static final DeferredItem<Item> LAVA_COMPONENT_2 = ITEMS.registerSimpleItem("lava_component_2");

    public static final DeferredItem<Item> EMPTY_CUP = ITEMS.registerSimpleItem("empty_cup");
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
    public static final DeferredItem<MintTeaItem> MINT_TEA = ITEMS.register("mint_tea",
            () -> new MintTeaItem(new Item.Properties()
                    .stacksTo(16)
                    .craftRemainder(EMPTY_CUP.get())
                    .food(new FoodProperties.Builder()
                            .alwaysEdible() // Allows drinking even when fully fed
                            .usingConvertsTo(EMPTY_CUP.get()) // Fallback return item
                            .build())
            ));
    public static final DeferredItem<BlockItem> INFINITE_LAVA_SOURCE =
            ITEMS.register("infinite_lava_source", () ->
                    new BlockItem(ModBlocks.INFINITE_LAVA_SOURCE.get(), new Item.Properties()));
    public static final DeferredItem<FeeshItem> FEESH = ITEMS.registerItem("feesh",
            FeeshItem::new,
            new Item.Properties().attributes(FeeshItem.createFeeshAttributes()));
    public static final DeferredItem<RedstoneCigaretteItem> REDSTONE_CIGARETTE =
            ITEMS.registerItem("redstone_cigarette", RedstoneCigaretteItem::new);
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
                output.accept(REDSTONE_CIGARETTE.get());
                output.accept(CHEESE_SLICE.get());
                output.accept(FEESH.get());
                // output.accept(MUSIC_DISC_BARKFART.get());
                output.accept(YOUNG_CHEESE_BIN.get());
                output.accept(REDSTONE_DIRT_ITEM.get());
                output.accept(REDSTONE_GRASS_ITEM.get());
                output.accept(REDSTONE_LOG_ITEM.get());
                output.accept(STRIPPED_REDSTONE_LOG_ITEM.get());
                output.accept(REDSTONE_WOOD_ITEM.get());
                output.accept(STRIPPED_REDSTONE_WOOD_ITEM.get());
                output.accept(REDSTONE_PLANKS_ITEM.get());
                output.accept(REDSTONE_STAIRS_ITEM.get());
                output.accept(REDSTONE_SLAB_ITEM.get());
                output.accept(REDSTONE_FENCE_ITEM.get());
                output.accept(REDSTONE_FENCE_GATE_ITEM.get());
                output.accept(REDSTONE_DOOR_ITEM.get());
                output.accept(REDSTONE_TRAPDOOR_ITEM.get());
                output.accept(REDSTONE_BUTTON_ITEM.get());
                output.accept(REDSTONE_PRESSURE_PLATE_ITEM.get());
                output.accept(REDSTONE_SIGN_ITEM.get());
                output.accept(REDSTONE_HANGING_SIGN_ITEM.get());
                output.accept(REDSTONE_LEAVES_ITEM.get());
                output.accept(REDSTONE_SAPLING_ITEM.get());
                output.accept(AGED_CHEESE_BIN.get());
                output.accept(LAVA_COMPONENT_1.get());
                output.accept(BASKET.get());
                output.accept(LAVA_COMPONENT_2.get());
                output.accept(BLOCK_OF_CHEESE.get());
                output.accept(MINT.get());
                output.accept(EMPTY_CUP.get());
                if (Config.ENABLE_INFINITE_LAVA_SOURCE.get()) {
                    output.accept(INFINITE_LAVA_SOURCE.get());
                }
                output.accept(MINT_TEA.get());
                output.accept(MINT_SEEDS.get());
                // output.accept(MUSIC_DISC_GOLDENBROWN.get());
                // output.accept(MUSIC_DISC_AUREASOL.get());
                output.accept(ModFluids.LIQUID_REDSTONE_BUCKET.get());
                output.accept(ModFluids.LIQUID_CHEESE_BUCKET.get());
                output.accept(ModFluids.LIQUID_MINT_BUCKET.get());
            }).build());

    public CreateLogansUtils(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeMod.enableMilkFluid();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(DataGenerators::gatherData);

        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ITEMS.register(modEventBus);
        ModMobEffects.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModJuicerRecipes.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ModRecipes.register(modEventBus);
        modEventBus.addListener(ModCapabilities::register);
        ModAttachments.register(modEventBus);
        modEventBus.addListener(this::addSignBlocks);
        ModFluids.register(modEventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ModPartialModels.init();
            modEventBus.addListener(com.logthedeveloper.createlogansutils.client.ClientSetup::onClientSetup);
        }

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void addSignBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                ModBlocks.REDSTONE_SIGN.get(), ModBlocks.REDSTONE_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.REDSTONE_HANGING_SIGN.get(), ModBlocks.REDSTONE_WALL_HANGING_SIGN.get());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("bonjour");
        event.enqueueWork(() -> {
            ModBlocks.registerFlammability();
            ModTerraBlender.register();
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // creative thing idk
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("if you see this, idk");
    }
}
