package sfiomn.legendary_additions.registry;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.*;
import sfiomn.legendary_additions.itemgroup.ModItemGroup;

import java.util.function.Supplier;

public class BlockRegistry
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LegendaryAdditions.MOD_ID);
	public static final RegistryObject<Block> CARVED_OAK_LOG_BLOCK = registerBlock("carved_oak_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CARVED_ACACIA_LOG_BLOCK = registerBlock("carved_acacia_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CARVED_BIRCH_LOG_BLOCK = registerBlock("carved_birch_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CARVED_JUNGLE_LOG_BLOCK = registerBlock("carved_jungle_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CARVED_DARK_OAK_LOG_BLOCK = registerBlock("carved_dark_oak_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));
	public static final RegistryObject<Block> CARVED_SPRUCE_LOG_BLOCK = registerBlock("carved_spruce_log", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));

	public static final RegistryObject<Block> ANCESTRAL_WOOD = registerBlock("ancestral_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1).noOcclusion()));

	public static final RegistryObject<Block> MOSS_BLOCK = registerBlock("moss", MossBlock::new);

	public static final RegistryObject<Block> HONEY_POND_BLOCK = BLOCKS.register("honey_pond", HoneyPondBlock::new);
	public static final RegistryObject<Block> MEAT_RACK_BLOCK = registerBlock("meat_rack", MeatRackBlock::new);
	public static final RegistryObject<Block> OBELISK_BLOCK = BLOCKS.register("obelisk", ObeliskBlock::new);
	public static final RegistryObject<Block> XP_STORAGE_BLOCK = registerBlock("xp_storage", XpStorageBlock::new);
	public static final RegistryObject<Block> CLOVER_PATCH_BLOCK = registerBlock("clover_patch", CloverPatchBlock::new);
	public static final RegistryObject<Block> GLOWING_BULB_BLOCK = registerBlock("glowing_bulb", () -> new DoublePlantBlock(AbstractBlock.Properties
			.of(Material.REPLACEABLE_PLANT).noCollission().sound(SoundType.GRASS).instabreak().lightLevel((p_235470_0_) -> 14).emissiveRendering((bs, br, bp) -> true)));
	public static final RegistryObject<Block> TRIBAL_TORCH_BLOCK = BLOCKS.register("tribal_torch", TribalTorchBlock::new);
	public static final RegistryObject<Block> TRIBAL_TORCH_DOWN_BLOCK = BLOCKS.register("tribal_torch_down", TribalTorchDownBlock::new);
	public static final RegistryObject<Block> TRIBAL_TORCH_WALL_BLOCK = BLOCKS.register("tribal_torch_wall", TribalTorchWallBlock::new);
	public static final RegistryObject<Block> HIVE_LANTERN_BLOCK = registerBlock("hive_lantern", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.WOOD).strength(1f, 10f).harvestTool(ToolType.AXE).harvestLevel(1)
			.lightLevel((p_235470_0_) -> {return 15;}).noOcclusion().requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> CAPTAIN_CHAIR_BLOCK = registerBlock("captain_chair", CaptainChairBlock::new);
	public static final RegistryObject<Block> CAPTAIN_CHAIR_TOP_BLOCK = BLOCKS.register("captain_chair_top", CaptainChairTopBlock::new);

	public static final RegistryObject<Block> ACACIA_WINDOW_PANE = registerBlock("acacia_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> ACACIA_WINDOW_BLOCK = registerBlock("acacia_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> BIRCH_WINDOW_PANE = registerBlock("birch_window_pane", () -> new PaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> BIRCH_WINDOW_BLOCK = registerBlock("birch_window", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> DARK_OAK_WINDOW_PANE = registerBlock("dark_oak_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> DARK_OAK_WINDOW_BLOCK = registerBlock("dark_oak_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> JUNGLE_WINDOW_PANE = registerBlock("jungle_window_pane", () -> new PaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> JUNGLE_WINDOW_BLOCK = registerBlock("jungle_window", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> OAK_WINDOW_PANE = registerBlock("oak_window_pane", () -> new PaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> OAK_WINDOW_BLOCK = registerBlock("oak_window", () -> new Block(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> SPRUCE_WINDOW_PANE = registerBlock("spruce_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> SPRUCE_WINDOW_BLOCK = registerBlock("spruce_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().harvestTool(ToolType.AXE)));
	public static final RegistryObject<Block> CRIMSON_WINDOW_PANE = registerBlock("crimson_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> CRIMSON_WINDOW_BLOCK = registerBlock("crimson_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(2f, 50f).harvestTool(ToolType.AXE).harvestLevel(4).noOcclusion()));
	public static final RegistryObject<Block> ORNATE_IRON_WINDOW_PANE = registerBlock("ornate_iron_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(2f, 50f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> ORNATE_IRON_WINDOW_BLOCK = registerBlock("ornate_iron_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.METAL).sound(SoundType.GLASS).strength(2f, 50f).harvestTool(ToolType.PICKAXE).harvestLevel(4).noOcclusion()));
	public static final RegistryObject<Block> WARPED_WINDOW_PANE = registerBlock("warped_window_pane", () -> new VerticalPaneBlock(AbstractBlock.Properties
			.of(Material.GLASS).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isViewBlocking((blockState, blockReader, blockPos) -> false)));
	public static final RegistryObject<Block> WARPED_WINDOW_BLOCK = registerBlock("warped_window", () -> new VerticalBlock(AbstractBlock.Properties
			.of(Material.WOOD).sound(SoundType.GLASS).strength(2f, 50f).harvestTool(ToolType.AXE).harvestLevel(4).noOcclusion()));

	public static final RegistryObject<Block> FOREST_DUNGEON_GATE_BLOCK = registerBlock("forest_dungeon_gate", ForestDungeonGateBlock::new);

	public static final RegistryObject<Block> FOREST_DUNGEON_HEART_BLOCK = registerBlock("forest_dungeon_heart", ForestDungeonHeartBlock::new);

	public static final RegistryObject<Block> SPIDER_EGGS_BLOCK = registerBlock("spider_eggs", SpiderEggsBlock::new);

	private static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> block) {
		RegistryObject<Block> newBlock = BLOCKS.register(name, block);
		registerBlockItem(name, newBlock);
		return newBlock;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));
	}

	public static void register(IEventBus eventBus){
		BLOCKS.register(eventBus);
	}
}
