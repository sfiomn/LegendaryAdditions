package sfiomn.legendary_additions.registry;

import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.effects.DungeonHeartEffect;

public class EffectRegistry
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<Effect> DUNGEON_HEART = EFFECTS.register("dungeon_heart", DungeonHeartEffect::new);

    public static void register (IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
