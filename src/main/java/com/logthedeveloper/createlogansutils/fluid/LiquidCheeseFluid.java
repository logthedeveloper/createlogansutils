package com.logthedeveloper.createlogansutils.fluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.LevelReader;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class LiquidCheeseFluid {

    public static class Source extends BaseFlowingFluid.Source {
        public Source(Properties properties) {
            super(properties);
        }

        @Override
        public int getTickDelay(LevelReader level) {
            return 30; // higher = slower flow. Water is 5, lava is 30 (overworld) / 10 (nether)
        }

        @Override
        public int getSlopeFindDistance(LevelReader level) {
            return 2; // lava uses 2, water uses 4 — lower = flows a shorter distance before stopping
        }
    }

    public static class Flowing extends BaseFlowingFluid.Flowing {
        public Flowing(Properties properties) {
            super(properties);
        }

        @Override
        public int getTickDelay(LevelReader level) {
            return 30;
        }

        @Override
        public int getSlopeFindDistance(LevelReader level) {
            return 2;
        }
    }
}