package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import sfiomn.legendary_additions.registry.ItemRegistry;
import sfiomn.legendary_additions.registry.SoundRegistry;

public class ForestKeyEntity extends KeyEntity {
    protected static final int successfulInsertAnimationTicks = 60;
    protected static final int successfulInsertSoundAtTick = 35;
    protected static final int failedInsertAnimationTicks = 80;
    public ForestKeyEntity(EntityType<? extends ForestKeyEntity> entityType, World world) {
        super(entityType, world);
        this.noPhysics = true;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int getSuccessfulInsertAnimationTicks() {
        return successfulInsertAnimationTicks;
    }

    @Override
    public int getSuccessfulInsertSoundAtTick() {
        return successfulInsertSoundAtTick;
    }

    @Override
    public int getFailedInsertAnimationTicks() {
        return failedInsertAnimationTicks;
    }

    @Override
    public Item getKeyItem() {
        return ItemRegistry.FOREST_KEY.get();
    }
}
