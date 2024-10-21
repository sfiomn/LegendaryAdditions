package sfiomn.legendary_additions.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import sfiomn.legendary_additions.LegendaryAdditions;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_GLOWING_BULB = registerKey("has_glowing_bulb");

    public static TagKey<Biome> registerKey(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(LegendaryAdditions.MOD_ID, name));
    }
}
