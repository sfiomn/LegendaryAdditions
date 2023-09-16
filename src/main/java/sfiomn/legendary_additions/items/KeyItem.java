package sfiomn.legendary_additions.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class KeyItem extends Item {
    public KeyItem(Properties properties) {
        super(properties);
    }

    public abstract Entity getKeyEntity(World world);
}
