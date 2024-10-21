package sfiomn.legendary_additions.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.util.ModBiomeTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, LegendaryAdditions.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(ModBiomeTags.HAS_GLOWING_BULB).addTag(Tags.Biomes.IS_PLAINS).addTag(Tags.Biomes.IS_CONIFEROUS).addTag(Tags.Biomes.IS_DENSE_OVERWORLD);
    }
}
