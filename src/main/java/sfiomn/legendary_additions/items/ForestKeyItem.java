package sfiomn.legendary_additions.items;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;

public class ForestKeyItem extends KeyItem{
    public ForestKeyItem(Properties properties) {
        super(properties);
    }

    public Entity getKeyEntity(World world) {
        return EntityTypeRegistry.FOREST_KEY_ENTITY.get().create(world);
    }
}
