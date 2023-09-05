package sfiomn.legendary_additions.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.world.gen.ModFlowerGeneration;

@Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModFlowerGeneration.generateFlowers(event);
    }
}
