package sfiomn.legendary_additions.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import sfiomn.legendary_additions.LegendaryAdditions;

public class ModTags {
    public static final TagKey<Item> CAN_DRY = registerKey("can_dry");

    public static TagKey<Item> registerKey(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(LegendaryAdditions.MOD_ID, name));
    }
}
