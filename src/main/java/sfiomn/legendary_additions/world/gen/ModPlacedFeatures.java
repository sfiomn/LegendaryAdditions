package sfiomn.legendary_additions.world.gen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import sfiomn.legendary_additions.LegendaryAdditions;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> GLOWING_BULB_PLACED_KEY = registerKey("glowing_bulb_placed");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LegendaryAdditions.MOD_ID, name));
    }

    public static List<PlacementModifier> worldSurfaceWithCountAndChance(int minCount, int maxCount, int chanceOnceEvery) {
        return List.of(
                RarityFilter.onAverageOnceEvery(chanceOnceEvery),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome(),
                CountPlacement.of(UniformInt.of(minCount, maxCount)));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context,
                GLOWING_BULB_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.GLOWING_BULB_CONFIG_KEY),
                worldSurfaceWithCountAndChance(4, 7, 20));
    }

}
