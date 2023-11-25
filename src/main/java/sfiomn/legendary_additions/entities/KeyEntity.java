package sfiomn.legendary_additions.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sfiomn.legendary_additions.tileentities.AbstractGateTileEntity;
import software.bernie.geckolib3.core.AnimationState;
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
    protected static final AnimationBuilder INSERT_SUCCESSFUL_ANIM = new AnimationBuilder().addAnimation("key_open", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder INSERT_FAILED_ANIM = new AnimationBuilder().addAnimation("key_fail", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private static final DataParameter<Integer> INSERT_ANIMATION = EntityDataManager.defineId(KeyEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> LIFE_TIME = EntityDataManager.defineId(KeyEntity.class, DataSerializers.INT);
    private static final DataParameter<BlockPos> GATE_TILE_ENTITY_POS = EntityDataManager.defineId(KeyEntity.class, DataSerializers.BLOCK_POS);
    public static final int NO_ANIMATION = 0;
    public static final int INSERT_SUCCESSFUL_KEY = 1;
    public static final int INSERT_FAILED_KEY = 2;
    public BlockPos gateBasePos;

    protected KeyEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.setLifeTime(getLifeTime() - 1);

        if (getAnimation() == INSERT_SUCCESSFUL_KEY && (getMaxAge() - getLifeTime()) == this.getSuccessfulInsertSoundAtTick()) {
            TileEntity te = this.getCommandSenderWorld().getBlockEntity(getGateTileEntityPos());
            if (te instanceof AbstractGateTileEntity)
                ((AbstractGateTileEntity) te).playUnlockSound(this.position());
        }

        if (getLifeTime() <= 0) {
            this.remove();
        }
    }

    public void successfulInsertInLock(BlockPos gateTileEntityPos) {
        this.setAnimation(INSERT_SUCCESSFUL_KEY);
        this.setLifeTime(getSuccessfulInsertAnimationTicks());
        this.setGateTileEntityPos(gateTileEntityPos);
    }

    public void failedInsertInLock(BlockPos gateTileEntityPos) {
        this.setAnimation(INSERT_FAILED_KEY);
        this.setLifeTime(getFailedInsertAnimationTicks());
        this.setGateTileEntityPos(gateTileEntityPos);
    }

    public <E extends IAnimatable> PlayState keyMovementPredicate(AnimationEvent<E> event) {
        if (getAnimation() == INSERT_SUCCESSFUL_KEY) {
            event.getController().setAnimation(INSERT_SUCCESSFUL_ANIM);
            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                return PlayState.STOP;
            }
        } else if (getAnimation() == INSERT_FAILED_KEY) {
            event.getController().setAnimation(INSERT_FAILED_ANIM);
            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                return PlayState.STOP;
            }
        }
        return PlayState.CONTINUE;
    }

    abstract public int getSuccessfulInsertAnimationTicks();
    abstract public int getSuccessfulInsertSoundAtTick();
    abstract public int getFailedInsertAnimationTicks();
    abstract public Item getKeyItem();

    public int getMaxAge() {
        if (getAnimation() == INSERT_SUCCESSFUL_KEY)
            return getSuccessfulInsertAnimationTicks();
        else if (getAnimation() == INSERT_FAILED_KEY)
            return getFailedInsertAnimationTicks();
        return 0;
    }

    public int getAnimation() {
        return this.entityData.get(INSERT_ANIMATION);
    }

    public void setAnimation(int animation) {
        this.entityData.set(INSERT_ANIMATION, animation);
    }

    public int getLifeTime() {
        return this.entityData.get(LIFE_TIME);
    }

    public void setLifeTime(int lifeTime) {
        this.entityData.set(LIFE_TIME, lifeTime);
    }

    public BlockPos getGateTileEntityPos() {
        return this.entityData.get(GATE_TILE_ENTITY_POS);
    }

    public void setGateTileEntityPos(BlockPos gateTileEntityPos) {
        this.entityData.set(GATE_TILE_ENTITY_POS, gateTileEntityPos);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(INSERT_ANIMATION, NO_ANIMATION);
        this.entityData.define(LIFE_TIME, -1);
        this.entityData.define(GATE_TILE_ENTITY_POS, BlockPos.ZERO);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        this.setAnimation(nbt.getInt("insertAnimation"));
        this.setLifeTime(nbt.getInt("lifeTime"));
        this.setGateTileEntityPos(new BlockPos(
                nbt.getInt("GateTileEntityPosX"),
                nbt.getInt("GateTileEntityPosY"),
                nbt.getInt("GateTileEntityPosZ")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("insertAnimation", getAnimation());
        nbt.putInt("lifeTime", getLifeTime());
        nbt.putInt("GateTileEntityPosX", this.getGateTileEntityPos().getX());
        nbt.putInt("GateTileEntityPosY", this.getGateTileEntityPos().getY());
        nbt.putInt("GateTileEntityPosZ", this.getGateTileEntityPos().getZ());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "keyMovement", 0, this::keyMovementPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
