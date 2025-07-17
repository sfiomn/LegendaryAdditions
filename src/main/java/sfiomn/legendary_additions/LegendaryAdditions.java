package sfiomn.legendary_additions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.blockentities.render.SeatRenderer;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.registry.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendaryAdditions.MOD_ID)
public class LegendaryAdditions
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "legendary_additions";
    public static Path configPath = FMLPaths.CONFIGDIR.get();

    public static boolean legendarySurvivalOverhaulLoaded = false;

    // modConfigPath used to create a config directory if necessary
    public static Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "legendary_additions");

    public LegendaryAdditions() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // Register the setup method for modloading
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::onModConfigLoadEvent);

        BlockRegistry.register(modBus);
        EntityTypeRegistry.register(modBus);
        ItemRegistry.register(modBus);
        SoundRegistry.register(modBus);
        BlockEntityRegistry.register(modBus);
        ParticleTypeRegistry.register(modBus);
        CreativeTabRegistry.register(modBus);

        Config.register();

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);

        modIntegration(forgeBus);
    }

    private void modIntegration(IEventBus forgeBus) {
        legendarySurvivalOverhaulLoaded = ModList.get().isLoaded("legendarysurvivaloverhaul");

        if (legendarySurvivalOverhaulLoaded)
            LOGGER.debug("Legendary Survival Overhaul is loaded, enabling compatibility");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Config.Baked.bakeCommon();

        NetworkHandler.register();
    }

    private void onModConfigLoadEvent(ModConfigEvent.Loading event)
    {
        final ModConfig config = event.getConfig();

        if (config.getSpec() == Config.COMMON_SPEC)
            Config.Baked.bakeCommon();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityTypeRegistry.SEAT_ENTITY.get(), SeatRenderer::new);

            event.registerEntityRenderer(EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.COMMON_XP_BOTTLE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.RARE_XP_BOTTLE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(EntityTypeRegistry.LEGENDARY_XP_BOTTLE_ENTITY.get(), ThrownItemRenderer::new);
        }
    }
}
