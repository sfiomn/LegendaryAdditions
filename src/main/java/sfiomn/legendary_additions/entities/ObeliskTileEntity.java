package sfiomn.legendary_additions.entities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
import sfiomn.legendary_additions.registry.TileEntityRegistry;
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

public class ObeliskTileEntity extends TileEntity implements IAnimatable {
    public int xpCapacity;
    private int xp;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private boolean downAnimDone;

    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    protected static final AnimationBuilder DOWN = new AnimationBuilder().addAnimation("empty", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder STOP = new AnimationBuilder().addAnimation("stopped", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public ObeliskTileEntity() {
        super(TileEntityRegistry.OBELISK_TILE_ENTITY.get());
        this.xp = this.xpCapacity = 0;
        this.downAnimDone = true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "animController", 0, this::animController));
    }

    protected <E extends ObeliskTileEntity> PlayState animController(final AnimationEvent<E> event) {
        if (this.xp > 0) {
            event.getController().setAnimation(IDLE);
        } else if (downAnimDone) {
            event.getController().setAnimation(DOWN);
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                downAnimDone = false;
            }
        } else {
            event.getController().setAnimation(STOP);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public int getXp() {
        return this.xp;
    }

    public int getXpCapacity() {
        return this.xpCapacity;
    }

    public void setXp(int xp) {
        this.xp = xp;
        if (xp == 0 && this.level != null)
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(ObeliskBlock.OBELISK_EMPTY, true));
        this.setChanged();
    }

    public void setXpCapacity(int xpCapacity) {
        this.xpCapacity = xpCapacity;
        this.setChanged();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.xp = nbt.getInt("xp");
        this.xpCapacity = nbt.getInt("xpCapacity");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("xp", this.xp);
        nbt.putInt("xpCapacity", this.xpCapacity);
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
        nbt.putInt("xp", this.xp);
        nbt.putInt("xpCapacity", this.xpCapacity);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.xp = nbt.getInt("xp");
        this.xpCapacity = nbt.getInt("xpCapacity");
    }
}
