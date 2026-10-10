package com.logthedeveloper.createlogansutils.datagen;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, CreateLogansUtils.MODID, helper);
    }

    private String name(Block b) { return BuiltInRegistries.BLOCK.getKey(b).getPath(); }

    private void itemFromBlockModel(Block b) {
        itemModels().withExistingParent(name(b), modLoc("block/" + name(b)));
    }

    private void itemFromBlockModel(Block b, String modelName) {
        itemModels().withExistingParent(name(b), modLoc("block/" + modelName));
    }

    @Override
    protected void registerStatesAndModels() {
        // dirt + grass
        simpleBlockWithItem(ModBlocks.REDSTONE_DIRT.get(), cubeAll(ModBlocks.REDSTONE_DIRT.get()));
        simpleBlockWithItem(ModBlocks.REDSTONE_GRASS.get(), models().cubeBottomTop("redstone_grass",
                modLoc("block/redstone_grass_side"), modLoc("block/redstone_dirt"), modLoc("block/redstone_grass_top")));

        // logs / wood
        logBlock(ModBlocks.REDSTONE_LOG.get());
        itemFromBlockModel(ModBlocks.REDSTONE_LOG.get());
        logBlock(ModBlocks.STRIPPED_REDSTONE_LOG.get());
        itemFromBlockModel(ModBlocks.STRIPPED_REDSTONE_LOG.get());
        axisBlock(ModBlocks.REDSTONE_WOOD.get(), blockTexture(ModBlocks.REDSTONE_LOG.get()), blockTexture(ModBlocks.REDSTONE_LOG.get()));
        itemFromBlockModel(ModBlocks.REDSTONE_WOOD.get());
        axisBlock(ModBlocks.STRIPPED_REDSTONE_WOOD.get(), blockTexture(ModBlocks.STRIPPED_REDSTONE_LOG.get()), blockTexture(ModBlocks.STRIPPED_REDSTONE_LOG.get()));
        itemFromBlockModel(ModBlocks.STRIPPED_REDSTONE_WOOD.get());

        // planks set
        var planks = ModBlocks.REDSTONE_PLANKS.get();
        simpleBlockWithItem(planks, cubeAll(planks));
        stairsBlock(ModBlocks.REDSTONE_STAIRS.get(), blockTexture(planks));
        itemFromBlockModel(ModBlocks.REDSTONE_STAIRS.get());
        slabBlock(ModBlocks.REDSTONE_SLAB.get(), blockTexture(planks), blockTexture(planks));
        itemFromBlockModel(ModBlocks.REDSTONE_SLAB.get());
        fenceBlock(ModBlocks.REDSTONE_FENCE.get(), blockTexture(planks));
        itemModels().withExistingParent("redstone_fence", mcLoc("block/fence_inventory"))
                .texture("texture", blockTexture(planks));
        fenceGateBlock(ModBlocks.REDSTONE_FENCE_GATE.get(), blockTexture(planks));
        itemFromBlockModel(ModBlocks.REDSTONE_FENCE_GATE.get());
        buttonBlock(ModBlocks.REDSTONE_BUTTON.get(), blockTexture(planks));
        itemModels().withExistingParent("redstone_button", mcLoc("block/button_inventory"))
                .texture("texture", blockTexture(planks));
        pressurePlateBlock(ModBlocks.REDSTONE_PRESSURE_PLATE.get(), blockTexture(planks));
        itemFromBlockModel(ModBlocks.REDSTONE_PRESSURE_PLATE.get(), "redstone_pressure_plate");

        // door / trapdoor (cutout)
        doorBlockWithRenderType(ModBlocks.REDSTONE_DOOR.get(),
                modLoc("block/redstone_door_bottom"), modLoc("block/redstone_door_top"), "cutout");
        itemModels().withExistingParent("redstone_door", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/redstone_door"));
        trapdoorBlockWithRenderType(ModBlocks.REDSTONE_TRAPDOOR.get(),
                modLoc("block/redstone_trapdoor"), true, "cutout");
        itemFromBlockModel(ModBlocks.REDSTONE_TRAPDOOR.get(), "redstone_trapdoor_bottom");

        // signs (particle-only models)
        ModelFile sign = models().sign("redstone_sign", blockTexture(planks));
        signBlock(ModBlocks.REDSTONE_SIGN.get(), ModBlocks.REDSTONE_WALL_SIGN.get(), sign);
        ModelFile hanging = models().sign("redstone_hanging_sign", blockTexture(ModBlocks.STRIPPED_REDSTONE_LOG.get()));
        simpleBlock(ModBlocks.REDSTONE_HANGING_SIGN.get(), hanging);
        simpleBlock(ModBlocks.REDSTONE_WALL_HANGING_SIGN.get(), hanging);
        itemModels().withExistingParent("redstone_sign", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/redstone_sign"));
        itemModels().withExistingParent("redstone_hanging_sign", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/redstone_hanging_sign"));

        // leaves + sapling
        var leaves = ModBlocks.REDSTONE_LEAVES.get();
        simpleBlockWithItem(leaves, models()
                .singleTexture("redstone_leaves", mcLoc("block/leaves"), "all", blockTexture(leaves))
                .renderType("cutout_mipped"));
        var sapling = ModBlocks.REDSTONE_SAPLING.get();
        simpleBlock(sapling, models().cross("redstone_sapling", blockTexture(sapling)).renderType("cutout"));
        itemModels().withExistingParent("redstone_sapling", mcLoc("item/generated"))
                .texture("layer0", modLoc("block/redstone_sapling"));
    }
}