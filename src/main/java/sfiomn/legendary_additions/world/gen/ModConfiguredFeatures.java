package sfiomn.legendary_additions.world.gen;

import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CLOVER_PATCH_CONFIG = Feature.FLOWER
            .configured((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.CLOVER_PATCH_BLOCK.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE))
                    .tries(Config.Baked.cloverPatchCount)
                    .build())
            .decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE)
            .count(Config.Baked.cloverPatchCount);

    public static final ConfiguredFeature<?, ?> GLOWING_BULB_CONFIG = Feature.RANDOM_PATCH
            .configured((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockRegistry.GLOWING_BULB_BLOCK.get().defaultBlockState()), DoublePlantBlockPlacer.INSTANCE))
                    .tries(Config.Baked.glowingBulbTries)
                    .noProjection()
                    .build())
            .count(FeatureSpread.of(1, 4))
            .decorated(Features.Placements.HEIGHTMAP_SQUARE)
            .chance(Config.Baked.glowingBulbCount);
}
