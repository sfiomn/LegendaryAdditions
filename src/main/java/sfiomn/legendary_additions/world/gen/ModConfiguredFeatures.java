package sfiomn.legendary_additions.world.gen;

import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CLOVER_PATCH_CONFIG = Feature.FLOWER
            .configured((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.CLOVER_PATCH_BLOCK.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE))
                    .tries(5)
                    .build())
            .decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE)
            .count(8);

    public static final ConfiguredFeature<?, ?> GLOWING_BULB_CONFIG = Feature.RANDOM_PATCH
            .configured((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.GLOWING_BULB_BLOCK.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE))
                    .tries(8)
                    .noProjection()
                    .build())
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8, 0, 1)));
}
