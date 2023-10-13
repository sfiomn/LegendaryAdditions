package sfiomn.legendary_additions.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<SoundEvent> IRON_ON_COAL = registerSoundEvent("iron_on_coal");
    public static final RegistryObject<SoundEvent> LOCK_UNLOCKED = registerSoundEvent("lock_unlocked");
    public static final RegistryObject<SoundEvent> OPEN_GATE_FAILED = registerSoundEvent("open_gate_failed");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(
                new ResourceLocation(LegendaryAdditions.MOD_ID, name)
            ));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
