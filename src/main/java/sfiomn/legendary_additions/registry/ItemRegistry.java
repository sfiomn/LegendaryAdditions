package sfiomn.legendary_additions.registry;

import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.itemgroup.ModItemGroup;
import sfiomn.legendary_additions.items.XpBottleItem;
import sfiomn.legendary_additions.util.XpBottleEnum;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<Item> TINY_XP_BOTTLE_ITEM = ITEMS.register("tiny_xp_bottle", () -> new XpBottleItem(XpBottleEnum.TINY, new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));
    public static final RegistryObject<Item> COMMON_XP_BOTTLE_ITEM = ITEMS.register("common_xp_bottle", () -> new XpBottleItem(XpBottleEnum.COMMON, new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));
    public static final RegistryObject<Item> RARE_XP_BOTTLE_ITEM = ITEMS.register("rare_xp_bottle", () -> new XpBottleItem(XpBottleEnum.RARE, new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));
    public static final RegistryObject<Item> EPIC_XP_BOTTLE_ITEM = ITEMS.register("epic_xp_bottle", () -> new XpBottleItem(XpBottleEnum.EPIC, new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));
    public static final RegistryObject<Item> LEGENDARY_XP_BOTTLE_ITEM = ITEMS.register("legendary_xp_bottle", () -> new XpBottleItem(XpBottleEnum.LEGENDARY, new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));

    public static final RegistryObject<Item> TRIBAL_TORCH = ITEMS.register("tribal_torch", () -> new WallOrFloorItem(BlockRegistry.TRIBAL_TORCH_BLOCK.get(), BlockRegistry.TRIBAL_TORCH_WALL_BLOCK.get(), new Item.Properties().tab(ModItemGroup.LEGENDARY_ADDITIONS_GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
