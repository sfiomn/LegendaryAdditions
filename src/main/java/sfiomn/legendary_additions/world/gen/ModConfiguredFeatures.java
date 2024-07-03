package sfiomn.legendary_additions.world.gen;

import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.FeatureRegistry;

import java.util.Collections;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CLOVER_PATCH_CONFIG = Feature.FLOWER
            .configured((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.CLOVER_PATCH_BLOCK.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE))
                    .tries(Config.Baked.cloverPatchTries)
                    .build())
            .decorated(Features.Placements.ADD_32)
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .count(Config.Baked.cloverPatchCount);

    public static final ConfiguredFeature<?, ?> GLOWING_BULB_CONFIG = FeatureRegistry.GLOWING_BULB.get().configured((new BlockClusterFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockRegistry.GLOWING_BULB_BLOCK.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE))
                    .tries(1)
                    .canReplace()
                    .build())
            .count(FeatureSpread.of(3, 5))
            .decorated(Placement.CHANCE.configured(new ChanceConfig(Config.Baked.glowingBulbSpawnChance)));
}
