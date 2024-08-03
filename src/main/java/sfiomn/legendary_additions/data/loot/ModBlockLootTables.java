package sfiomn.legendary_additions.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.registry.BlockRegistry;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockRegistry.CARVED_ACACIA_LOG_BLOCK.get());
        this.dropSelf(BlockRegistry.CARVED_BIRCH_LOG_BLOCK.get());
        this.dropSelf(BlockRegistry.CARVED_DARK_OAK_LOG_BLOCK.get());
        this.dropSelf(BlockRegistry.CARVED_JUNGLE_LOG_BLOCK.get());
        this.dropSelf(BlockRegistry.CARVED_OAK_LOG_BLOCK.get());
        this.dropSelf(BlockRegistry.CARVED_SPRUCE_LOG_BLOCK.get());

        this.dropSelf(BlockRegistry.ANCESTRAL_WOOD.get());
        this.dropSelf(BlockRegistry.CAPTAIN_CHAIR_BLOCK.get());
        this.dropSelf(BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get());
        this.dropSelf(BlockRegistry.GLOWING_BULB_BLOCK.get());
        this.dropSelf(BlockRegistry.HIVE_LANTERN_BLOCK.get());
        this.dropSelf(BlockRegistry.HONEY_POND_BLOCK.get());
        this.dropSelf(BlockRegistry.MEAT_RACK_BLOCK.get());
        this.dropSelf(BlockRegistry.OBELISK_BLOCK.get());

        this.dropSelf(BlockRegistry.TRIBAL_TORCH_BLOCK.get());
        this.dropOther(BlockRegistry.TRIBAL_TORCH_DOWN_BLOCK.get(), BlockRegistry.TRIBAL_TORCH_BLOCK.get());
        this.dropOther(BlockRegistry.TRIBAL_TORCH_WALL_BLOCK.get(), BlockRegistry.TRIBAL_TORCH_BLOCK.get());
    }

    protected LootTable.Builder createSimpleDropCount(Block block, Item item, float min, float max) {
        return createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
