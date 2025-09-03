package sfiomn.legendary_additions.registry;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.items.*;
import sfiomn.legendary_additions.util.XpBottleEnum;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryAdditions.MOD_ID);

    public static final RegistryObject<Item> TINY_XP_BOTTLE_ITEM = ITEMS.register("tiny_xp_bottle", () -> new XpBottleItem(XpBottleEnum.TINY, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> COMMON_XP_BOTTLE_ITEM = ITEMS.register("common_xp_bottle", () -> new XpBottleItem(XpBottleEnum.COMMON, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> RARE_XP_BOTTLE_ITEM = ITEMS.register("rare_xp_bottle", () -> new XpBottleItem(XpBottleEnum.RARE, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EPIC_XP_BOTTLE_ITEM = ITEMS.register("epic_xp_bottle", () -> new XpBottleItem(XpBottleEnum.EPIC, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LEGENDARY_XP_BOTTLE_ITEM = ITEMS.register("legendary_xp_bottle", () -> new XpBottleItem(XpBottleEnum.LEGENDARY, new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> DEATH_SCROLL_ITEM = ITEMS.register("death_scroll", () -> new DeathScrollItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HONEY_POND_BLOCK_ITEM = ITEMS.register("honey_pond", () -> new HoneyPondItem(BlockRegistry.HONEY_POND_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> OBELISK_BLOCK_ITEM = ITEMS.register("obelisk", () -> new ObeliskItem(BlockRegistry.OBELISK_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> TRIBAL_TORCH = ITEMS.register("tribal_torch", () -> new StandingAndWallBlockItem(BlockRegistry.TRIBAL_TORCH_BLOCK.get(), BlockRegistry.TRIBAL_TORCH_WALL_BLOCK.get(), new Item.Properties(), Direction.DOWN));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
