package sfiomn.legendary_additions;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.entities.render.ObeliskBlockRenderer;
import sfiomn.legendary_additions.entities.render.SeatRenderer;
import sfiomn.legendary_additions.registry.*;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
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
    public static Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "legendary_additions");

    public LegendaryAdditions() {

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        BlockRegistry.register(modBus);
        ItemRegistry.register(modBus);
        EntityTypeRegistry.register(modBus);
        SoundRegistry.register(modBus);
        TileEntityRegistry.register(modBus);

        Config.register();
        Config.Baked.bakeCommon();

        // Register the setup method for modloading
        modBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        modBus.addListener(this::doClientStuff);

        GeckoLib.initialize();

        forgeBus.addListener(this::reloadJsonConfig);

        // Register ourselves for server and other game events we are interested in
        forgeBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() ->
        {
            RenderTypeLookup.setRenderLayer(BlockRegistry.MEAT_RACK_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.OBELISK_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.CLOVER_PATCH_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.GLOWING_BULB_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.CAPTAIN_CHAIR_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.TRIBAL_TORCH_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.TRIBAL_TORCH_WALL_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.HIVE_LANTERN_BLOCK.get(), RenderType.cutout());

            RenderTypeLookup.setRenderLayer(BlockRegistry.CRIMSON_WINDOW_PANE.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(BlockRegistry.ORNATE_IRON_WINDOW_PANE.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(BlockRegistry.WARPED_WINDOW_PANE.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(BlockRegistry.CRIMSON_WINDOW_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.ORNATE_IRON_WINDOW_BLOCK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.WARPED_WINDOW_BLOCK.get(), RenderType.cutout());
        });

        DistExecutor.safeRunWhenOn(Dist.CLIENT, LegendaryAdditions::registerEntityRendering);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, LegendaryAdditions::registerTileEntityRenderer);
    }

    private static DistExecutor.SafeRunnable registerEntityRendering() {

        return new DistExecutor.SafeRunnable()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void run()
            {
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.SEAT_ENTITY.get(), SeatRenderer::new);

                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.TINY_XP_BOTTLE_ENTITY.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.COMMON_XP_BOTTLE_ENTITY.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.RARE_XP_BOTTLE_ENTITY.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.EPIC_XP_BOTTLE_ENTITY.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
                RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.LEGENDARY_XP_BOTTLE_ENTITY.get(), renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
            }
        };
    }

    private static DistExecutor.SafeRunnable registerTileEntityRenderer() {

        return new DistExecutor.SafeRunnable()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void run()
            {
                ClientRegistry.bindTileEntityRenderer(TileEntityRegistry.OBELISK_TILE_ENTITY.get(), ObeliskBlockRenderer::new);
            }
        };
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    private void reloadJsonConfig(final AddReloadListenerEvent event)
    {
        event.addListener(new ReloadListener<Void>()
              {
                  @Nonnull
                  @ParametersAreNonnullByDefault
                  @Override
                  protected Void prepare(IResourceManager manager, IProfiler profiler)
                  {
                      return null;
                  }

                  @ParametersAreNonnullByDefault
                  @Override
                  protected void apply(Void objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn)
                  {
                      Config.Baked.bakeCommon();
                  }

              }
        );
    }
}
