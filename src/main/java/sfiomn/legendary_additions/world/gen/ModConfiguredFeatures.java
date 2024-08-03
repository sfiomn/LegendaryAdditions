package sfiomn.legendary_additions.world.gen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_BULB_CONFIG_KEY = registerKey("glowing_bulb_config_feature");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(LegendaryAdditions.MOD_ID, name));
    }

    public static void registerSimpleRandomPatchConfig(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, BlockState blockState) {
        FeatureUtils.register(context,
                resourceKey,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(1, 5, 3,
                        PlacementUtils.filtered(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(blockState)),
                                BlockPredicate.replaceable())));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleRandomPatchConfig(context, GLOWING_BULB_CONFIG_KEY, BlockRegistry.GLOWING_BULB_BLOCK.get().defaultBlockState());
    }
}
