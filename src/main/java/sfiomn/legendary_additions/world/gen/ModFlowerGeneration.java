package sfiomn.legendary_additions.world.gen;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ModFlowerGeneration {

    public static void generateFlowers(final BiomeLoadingEvent event) {

        ResourceLocation biomeName = event.getName();
        Biome.Category biomeCategory = event.getCategory();

        boolean canCloverPatchSpawn = false;
        boolean canGlowingBulbSpawn = false;

        if (biomeName != null) {
            canCloverPatchSpawn = Config.Baked.cloverPatchBiomeNames.contains(biomeName.toString());
            canGlowingBulbSpawn = Config.Baked.glowingBulbBiomeNames.contains(biomeName.toString());
        }

        if (!canCloverPatchSpawn) {
            canCloverPatchSpawn = Config.Baked.cloverPatchBiomeCategories.contains(biomeCategory.getName()) ||
                    Config.Baked.cloverPatchBiomeCategories.contains(biomeCategory.toString());
        }

        if (!canGlowingBulbSpawn) {
            canGlowingBulbSpawn = Config.Baked.glowingBulbBiomeCategories.contains(biomeCategory.getName()) ||
                    Config.Baked.glowingBulbBiomeCategories.contains(biomeCategory.toString());
        }

        if (canCloverPatchSpawn) {
            LegendaryAdditions.LOGGER.debug("Add clover patch in biome " + biomeName + " with category " + biomeCategory);

            List<Supplier<ConfiguredFeature<?, ?>>> base = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
            base.add(() -> ModConfiguredFeatures.CLOVER_PATCH_CONFIG);
        }

        if (canGlowingBulbSpawn) {
            LegendaryAdditions.LOGGER.debug("Add glowing bulb in biome " + biomeName + " with category " + biomeCategory);

            List<Supplier<ConfiguredFeature<?, ?>>> base = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
            base.add(() -> ModConfiguredFeatures.GLOWING_BULB_CONFIG);
        }
    }
}
