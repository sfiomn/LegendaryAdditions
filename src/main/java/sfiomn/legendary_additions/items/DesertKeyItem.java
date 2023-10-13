package sfiomn.legendary_additions.items;

import net.minecraft.entity.EntityType;
import sfiomn.legendary_additions.entities.KeyEntity;
import sfiomn.legendary_additions.registry.EntityTypeRegistry;

public class DesertKeyItem extends KeyItem {
    public DesertKeyItem(Properties properties) {
        super(properties);
    }

    public EntityType<? extends KeyEntity> getKeyEntity() {
        return EntityTypeRegistry.DESERT_KEY_ENTITY.get();
    }
}
