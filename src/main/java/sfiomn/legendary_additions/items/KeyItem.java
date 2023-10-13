package sfiomn.legendary_additions.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import sfiomn.legendary_additions.entities.KeyEntity;

public abstract class KeyItem extends Item {
    public KeyItem(Properties properties) {
        super(properties);
    }

    public abstract EntityType<? extends KeyEntity> getKeyEntity();
}
