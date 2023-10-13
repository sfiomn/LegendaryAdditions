package sfiomn.legendary_additions.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.ForestKeyEntity;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;

@Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {

        //event.put(EntityTypeRegistry.FOREST_KEY_ENTITY.get(), ForestKeyEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        //Minecraft.getInstance().particleEngine.register(ParticleTypeRegistry.WISP_PARTICLE.get(), WispParticle.Factory::new);
        //Minecraft.getInstance().particleEngine.register(ParticleTypeRegistry.CORPSE_SPLATTER.get(), CorpseSplatter.Factory::new);
    }
}
