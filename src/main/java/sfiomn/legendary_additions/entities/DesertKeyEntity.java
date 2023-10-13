package sfiomn.legendary_additions.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.ItemRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class DesertKeyEntity extends KeyEntity {
    protected static final int insertAnimationTicks = 60;
    protected static final int ejectAnimationTicks = 80;
    public DesertKeyEntity(EntityType<? extends DesertKeyEntity> entityType, World world) {
        super(entityType, world);
        this.noPhysics = true;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int getSuccessfulInsertAnimationTicks() {
        return insertAnimationTicks;
    }

    @Override
    public int getFailedInsertAnimationTicks() {
        return ejectAnimationTicks;
    }

    @Override
    public Item getKeyItem() {
        return ItemRegistry.DESERT_KEY.get();
    }
}
