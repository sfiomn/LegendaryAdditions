package sfiomn.legendary_additions.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import sfiomn.legendary_additions.registry.ItemRegistry;

public class ModItemGroup {

    public static final ItemGroup LEGENDARY_ADDITIONS_GROUP = new ItemGroup("legendary_additions") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.LEGENDARY_XP_BOTTLE_ITEM.get());
        }
    };
}
