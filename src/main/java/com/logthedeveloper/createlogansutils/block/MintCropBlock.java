package com.logthedeveloper.createlogansutils.block;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class MintCropBlock extends CropBlock {

    public static final MapCodec<MintCropBlock> CODEC = simpleCodec(MintCropBlock::new);

    public MintCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CreateLogansUtils.MINT_SEEDS.get();
    }

    @Override
    public int getMaxAge() {
        return 3;
    }
}