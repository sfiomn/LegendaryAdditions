package sfiomn.legendary_additions.registry;

import net.minecraft.potion.Effect;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.effects.DungeonHeartEffect;
import sfiomn.legendary_additions.world.gen.feature.GlowingBulbFeature;

public class FeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> GLOWING_BULB = FEATURES.register("glowing_bulb", () -> new GlowingBulbFeature(BlockClusterFeatureConfig.CODEC));

    public static void register(IEventBus eventBus){
        FEATURES.register(eventBus);
    }
}
