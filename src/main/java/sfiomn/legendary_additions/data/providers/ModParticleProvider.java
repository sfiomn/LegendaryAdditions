package sfiomn.legendary_additions.data.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.ParticleTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModParticleProvider extends ParticleDescriptionProvider {
    public ModParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        List<ResourceLocation> poisonSmokeSprites = new ArrayList<>();
        for (int i=11; i>=0; i--) {
            poisonSmokeSprites.add(new ResourceLocation(LegendaryAdditions.MOD_ID, "poison_smoke_" + i));
        }
        spriteSet(ParticleTypeRegistry.POISON_SMOKE.get(), poisonSmokeSprites);
    }
}
