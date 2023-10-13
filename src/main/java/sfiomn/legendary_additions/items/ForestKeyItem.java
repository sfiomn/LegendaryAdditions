package sfiomn.legendary_additions.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import sfiomn.legendary_additions.entities.ForestKeyEntity;
import sfiomn.legendary_additions.entities.KeyEntity;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;

public class ForestKeyItem extends KeyItem {
    public ForestKeyItem(Properties properties) {
        super(properties);
    }

    public EntityType<? extends KeyEntity> getKeyEntity() {
        return EntityTypeRegistry.FOREST_KEY_ENTITY.get();
    }
}
