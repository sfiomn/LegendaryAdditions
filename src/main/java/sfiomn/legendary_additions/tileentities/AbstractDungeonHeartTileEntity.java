package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.EffectRegistry;
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

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class AbstractDungeonHeartTileEntity extends TileEntity implements ITickableTileEntity, IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    protected static final AnimationBuilder DEACTIVATED = new AnimationBuilder().addAnimation("deactivated", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private int rangeXPos;
    private int rangeXNeg;
    private int rangeYPos;
    private int rangeYNeg;
    private int rangeZPos;
    private int rangeZNeg;
    private boolean active;
    private int updateCounter;
    public AbstractDungeonHeartTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        this.active = true;
        this.rangeXPos = this.rangeXNeg = this.rangeYPos = this.rangeYNeg = this.rangeZPos = this.rangeZNeg = 0;
    }

    @Override
    public void tick() {
        if (this.level == null)
            return;

        if(isActive() && !this.level.isClientSide){
            if(this.updateCounter++ > 60){
                AxisAlignedBB box = new AxisAlignedBB(this.worldPosition.getX()-rangeXNeg,
                        this.worldPosition.getY()-rangeYNeg,
                        this.worldPosition.getZ()-rangeZNeg,
                        this.worldPosition.getX()+rangeXPos+1,
                        this.worldPosition.getY()+rangeYPos+1,
                        this.worldPosition.getZ()+rangeZPos+1);

                for (PlayerEntity player: this.level.players())
                    if (box.contains(player.position()))
                        player.addEffect(new EffectInstance(EffectRegistry.DUNGEON_HEART.get(), 120, 0, false, false, true));
            }
        }
    }

    protected <E extends AbstractDungeonHeartTileEntity> PlayState animController(final AnimationEvent<E> event) {
        if (this.isActive()) {
            event.getController().setAnimation(IDLE);
        } else {
            event.getController().setAnimation(DEACTIVATED);
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                return PlayState.STOP;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "animController", 10, this::animController));
    }

    public void setRange(Direction direction, int rangeValue) {
        switch (direction) {
            case EAST:
                this.setRangeXPos(rangeValue);
                break;
            case WEST:
                this.setRangeXNeg(rangeValue);
                break;
            case UP:
                this.setRangeYPos(rangeValue);
                break;
            case DOWN:
                this.setRangeYNeg(rangeValue);
                break;
            case SOUTH:
                this.setRangeZPos(rangeValue);
                break;
            case NORTH:
                this.setRangeZNeg(rangeValue);
                break;
        }
    }

    public void setRangeXPos(int rangeValue) {
        this.rangeXPos = rangeValue;
        this.updateClient();
    }

    public void setRangeXNeg(int rangeValue) {
        this.rangeXNeg = rangeValue;
        this.updateClient();
    }

    public void setRangeYPos(int rangeValue) {
        this.rangeYPos = rangeValue;
        this.updateClient();
    }

    public void setRangeYNeg(int rangeValue) {
        this.rangeYNeg = rangeValue;
        updateClient();
    }

    public void setRangeZPos(int rangeValue) {
        this.rangeZPos = rangeValue;
        updateClient();
    }

    public void setRangeZNeg(int rangeValue) {
        this.rangeZNeg = rangeValue;
        updateClient();
    }

    public void setActive(boolean active) {
        this.active = active;
        updateClient();
    }

    private void updateClient() {
        if (this.level == null)
            return;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public int getRangeXPos() {
        return rangeXPos;
    }

    public int getRangeXNeg() {
        return rangeXNeg;
    }

    public int getRangeYPos() {
        return rangeYPos;
    }

    public int getRangeYNeg() {
        return rangeYNeg;
    }

    public int getRangeZPos() {
        return rangeZPos;
    }

    public int getRangeZNeg() {
        return rangeZNeg;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.setRangeXPos(nbt.getInt("rangeXPos"));
        this.setRangeXNeg(nbt.getInt("rangeXNeg"));
        this.setRangeYPos(nbt.getInt("rangeYPos"));
        this.setRangeYNeg(nbt.getInt("rangeYNeg"));
        this.setRangeZPos(nbt.getInt("rangeZPos"));
        this.setRangeZNeg(nbt.getInt("rangeZNeg"));
        this.setActive(nbt.getBoolean("active"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("rangeXPos", this.getRangeXPos());
        nbt.putInt("rangeXNeg", this.getRangeXNeg());
        nbt.putInt("rangeYPos", this.getRangeYPos());
        nbt.putInt("rangeYNeg", this.getRangeYNeg());
        nbt.putInt("rangeZPos", this.getRangeZPos());
        nbt.putInt("rangeZNeg", this.getRangeZNeg());
        nbt.putBoolean("active", this.isActive());
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = this.getUpdateTag();
        return new SUpdateTileEntityPacket(this.worldPosition, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.putInt("rangeXPos", this.getRangeXPos());
        nbt.putInt("rangeXNeg", this.getRangeXNeg());
        nbt.putInt("rangeYPos", this.getRangeYPos());
        nbt.putInt("rangeYNeg", this.getRangeYNeg());
        nbt.putInt("rangeZPos", this.getRangeZPos());
        nbt.putInt("rangeZNeg", this.getRangeZNeg());
        nbt.putBoolean("active", this.isActive());
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.setRangeXPos(nbt.getInt("rangeXPos"));
        this.setRangeXNeg(nbt.getInt("rangeXNeg"));
        this.setRangeYPos(nbt.getInt("rangeYPos"));
        this.setRangeYNeg(nbt.getInt("rangeYNeg"));
        this.setRangeZPos(nbt.getInt("rangeZPos"));
        this.setRangeZNeg(nbt.getInt("rangeZNeg"));
        this.setActive(nbt.getBoolean("active"));
    }
}
