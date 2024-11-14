package sfiomn.legendary_additions.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.*;
import sfiomn.legendary_additions.blocks.MossBlock;

import java.util.function.Supplier;

public class BlockRegistry
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LegendaryAdditions.MOD_ID);
	public static final RegistryObject<Block> CARVED_OAK_LOG_BLOCK = registerBlock("carved_oak_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> CARVED_ACACIA_LOG_BLOCK = registerBlock("carved_acacia_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> CARVED_BIRCH_LOG_BLOCK = registerBlock("carved_birch_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> CARVED_JUNGLE_LOG_BLOCK = registerBlock("carved_jungle_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> CARVED_DARK_OAK_LOG_BLOCK = registerBlock("carved_dark_oak_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> CARVED_SPRUCE_LOG_BLOCK = registerBlock("carved_spruce_log", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));

	public static final RegistryObject<Block> ANCESTRAL_WOOD = registerBlock("ancestral_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion()));
	public static final RegistryObject<Block> HIVE_LANTERN_BLOCK = registerBlock("hive_lantern", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 10f)
			.lightLevel((blockState) -> 15).noOcclusion().requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> QUARTZ_LAMP_BLACK = registerBlock("quartz_lamp_black", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_BLACK).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_BLUE = registerBlock("quartz_lamp_blue", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_BLUE).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_BROWN = registerBlock("quartz_lamp_brown", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_BROWN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_CYAN = registerBlock("quartz_lamp_cyan", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_CYAN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_GRAY = registerBlock("quartz_lamp_gray", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_GRAY).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_GREEN = registerBlock("quartz_lamp_green", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_GREEN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_LIGHT_BLUE = registerBlock("quartz_lamp_light_blue", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_LIGHT_GRAY = registerBlock("quartz_lamp_light_gray", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_LIME = registerBlock("quartz_lamp_lime", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_LIGHT_GREEN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_MAGENTA = registerBlock("quartz_lamp_magenta", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_CYAN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_ORANGE = registerBlock("quartz_lamp_orange", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_CYAN).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_PINK = registerBlock("quartz_lamp_pink", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_PINK).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_PURPLE = registerBlock("quartz_lamp_purple", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_PURPLE).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_RED = registerBlock("quartz_lamp_red", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_RED).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_WHITE = registerBlock("quartz_lamp_white", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.TERRACOTTA_WHITE).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
	public static final RegistryObject<Block> QUARTZ_LAMP_YELLOW = registerBlock("quartz_lamp_yellow", () -> new RedstoneLampBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.COLOR_YELLOW).lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));

	public static final RegistryObject<Block> MOSS_BLOCK = registerBlock("moss", MossBlock::new);
	public static final RegistryObject<Block> MUD_TRAP_BLOCK = registerBlock("mud_trap", MudTrapBlock::new);
	public static final RegistryObject<Block> POISON_GAS_BLOCK = registerBlock("poison_gas", () -> new PoisonGasBlock(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable()));
	public static final RegistryObject<Block> GLOWING_BULB_BLOCK = registerBlock("glowing_bulb", () -> new DoublePlantBlock(BlockBehaviour.Properties
			.of().noCollission().sound(SoundType.GRASS).instabreak().lightLevel((p_235470_0_) -> 14).emissiveRendering((bs, br, bp) -> true)));

	public static final RegistryObject<Block> HONEY_POND_BLOCK = BLOCKS.register("honey_pond", HoneyPondBlock::new);
	public static final RegistryObject<Block> MEAT_RACK_BLOCK = registerBlock("meat_rack", MeatRackBlock::new);
	public static final RegistryObject<Block> OBELISK_BLOCK = BLOCKS.register("obelisk", ObeliskBlock::new);
	public static final RegistryObject<Block> XP_STORAGE_BLOCK = registerBlock("xp_storage", XpStorageBlock::new);

	public static final RegistryObject<Block> TRIBAL_TORCH_BLOCK = BLOCKS.register("tribal_torch", TribalTorchBlock::new);
	public static final RegistryObject<Block> TRIBAL_TORCH_DOWN_BLOCK = BLOCKS.register("tribal_torch_down", TribalTorchDownBlock::new);
	public static final RegistryObject<Block> TRIBAL_TORCH_WALL_BLOCK = BLOCKS.register("tribal_torch_wall", TribalTorchWallBlock::new);

	public static final RegistryObject<Block> CAPTAIN_CHAIR_BLOCK = registerBlock("captain_chair", CaptainChairBlock::new);
	public static final RegistryObject<Block> CAPTAIN_CHAIR_TOP_BLOCK = BLOCKS.register("captain_chair_top", CaptainChairTopBlock::new);

	public static final RegistryObject<Block> ACACIA_WINDOW_PANE = registerBlock("acacia_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockGetter, blockPos) -> false)));
	public static final RegistryObject<Block> ACACIA_WINDOW_BLOCK = registerBlock("acacia_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> BIRCH_WINDOW_PANE = registerBlock("birch_window_pane", () -> new IronBarsBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> BIRCH_WINDOW_BLOCK = registerBlock("birch_window", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> DARK_OAK_WINDOW_PANE = registerBlock("dark_oak_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> DARK_OAK_WINDOW_BLOCK = registerBlock("dark_oak_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> JUNGLE_WINDOW_PANE = registerBlock("jungle_window_pane", () -> new IronBarsBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> JUNGLE_WINDOW_BLOCK = registerBlock("jungle_window", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> OAK_WINDOW_PANE = registerBlock("oak_window_pane", () -> new IronBarsBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> OAK_WINDOW_BLOCK = registerBlock("oak_window", () -> new Block(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> SPRUCE_WINDOW_PANE = registerBlock("spruce_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> SPRUCE_WINDOW_BLOCK = registerBlock("spruce_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> CRIMSON_WINDOW_PANE = registerBlock("crimson_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> CRIMSON_WINDOW_BLOCK = registerBlock("crimson_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(2f, 50f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> ORNATE_IRON_WINDOW_PANE = registerBlock("ornate_iron_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(2f, 50f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> ORNATE_IRON_WINDOW_BLOCK = registerBlock("ornate_iron_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.METAL).sound(SoundType.GLASS).strength(2f, 50f).noLootTable().noOcclusion()));
	public static final RegistryObject<Block> WARPED_WINDOW_PANE = registerBlock("warped_window_pane", () -> new VerticalPaneBlock(BlockBehaviour.Properties
			.of().sound(SoundType.GLASS).strength(1f, 10f).noLootTable().noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> WARPED_WINDOW_BLOCK = registerBlock("warped_window", () -> new VerticalBlock(BlockBehaviour.Properties
			.of().mapColor(MapColor.WOOD).sound(SoundType.GLASS).strength(2f, 50f).noLootTable().noOcclusion()));

	public static final RegistryObject<Block> SPIDER_EGGS_BLOCK = registerBlock("spider_eggs", SpiderEggsBlock::new);

	private static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> block) {
		RegistryObject<Block> newBlock = BLOCKS.register(name, block);
		registerBlockItem(name, newBlock);
		return newBlock;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus){
		BLOCKS.register(eventBus);
	}
}
