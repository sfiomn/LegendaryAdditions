package sfiomn.legendary_additions.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sfiomn.legendary_additions.tileentities.AbstractDungeonGateTileEntity;
import sfiomn.legendary_additions.util.Lock;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public abstract class KeyEntity extends Entity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected static final AnimationBuilder INSERTING = new AnimationBuilder().addAnimation("inserting", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private static final DataParameter<Integer> INSERT_ANIMATION = EntityDataManager.defineId(KeyEntity.class, DataSerializers.INT);
    private static final DataParameter<BlockPos> GATE_TILE_ENTITY_POS = EntityDataManager.defineId(KeyEntity.class, DataSerializers.BLOCK_POS);

    private Lock insertedLock;
    private AbstractDungeonGateTileEntity dungeonGateTileEntity;
    public static final int NO_ANIMATION = 0;
    public static final int INSERT_KEY = 1;
    public static final int TRY_UNLOCK = 1;
    public static final int EJECT_KEY = 2;
    public static final int DROP_KEY = 2;

    public KeyEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public abstract  <E extends IAnimatable> PlayState keyMovementPredicate(AnimationEvent<E> event);

    public void insertInLock(Lock lock, AbstractDungeonGateTileEntity tileEntity) {
        this.setAnimation(INSERT_KEY);
        this.insertedLock = lock;
        this.dungeonGateTileEntity = tileEntity;
    }

    public void tryUnlock() {
        if (this.insertedLock == null || this.dungeonGateTileEntity == null)
            return;

        if (this.insertedLock.canBeUnlocked(getKeyItem())) {
            TileEntity tileEntity = this.level.getBlockEntity(this.dungeonGateTileEntity.getBlockPos());
            if (tileEntity instanceof AbstractDungeonGateTileEntity) {
                AbstractDungeonGateTileEntity dungeonGateTE = (AbstractDungeonGateTileEntity) tileEntity;
                dungeonGateTE.unlock(this.insertedLock);
                this.remove();
            }
        } else {
            this.setAnimation(EJECT_KEY);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getAnimation() == TRY_UNLOCK) {
            tryUnlock();
        }

        if (this.getAnimation() == DROP_KEY) {
            Entity keyEntity = new ItemEntity(this.level, this.position().x, this.position().y, this.position().z, new ItemStack(this.getKeyItem()));
            this.level.addFreshEntity(keyEntity);
            this.remove();
        }
    }

    abstract public Item getKeyItem();

    public int getAnimation() {
        return this.entityData.get(INSERT_ANIMATION);
    }

    public void setAnimation(int animation) {
        this.entityData.set(INSERT_ANIMATION, animation);
    }

    @Override
    protected void defineSynchedData() {
        //this.entityData.define(GATE_TILE_ENTITY_POS, BlockPos.ZERO);
        this.entityData.define(INSERT_ANIMATION, NO_ANIMATION);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "keyMovement", 4, this::keyMovementPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
