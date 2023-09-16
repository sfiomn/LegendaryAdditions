package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
import sfiomn.legendary_additions.network.NetworkHandler;
import sfiomn.legendary_additions.network.packets.MessageObeliskDown;
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

    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    protected static final AnimationBuilder DOWN = new AnimationBuilder().addAnimation("empty", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final AnimationBuilder STOP = new AnimationBuilder().addAnimation("stopped", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public ObeliskTileEntity() {
        super(TileEntityRegistry.OBELISK_TILE_ENTITY.get());
        this.xp = this.xpCapacity = 0;
    }

    @Override
    public void registerControllers(AnimationData data) {
        if (!isDown())
            data.addAnimationController(new AnimationController<>(this, "animController", 0, this::animController));
    }

    protected <E extends ObeliskTileEntity> PlayState animController(final AnimationEvent<E> event) {
        if (this.getXp() > 0) {
            event.getController().setAnimation(IDLE);
        } else {
            event.getController().setAnimation(DOWN);
            if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                this.setDown();
                return PlayState.STOP;
            }
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

    public boolean isDown() {
        boolean down = true;
        if (this.level != null && this.level.getBlockState(this.worldPosition).hasProperty(ObeliskBlock.OBELISK_DOWN))
            down = this.level.getBlockState(this.worldPosition).getValue(ObeliskBlock.OBELISK_DOWN);
        return down;
    }

    public void setXp(int xp) {
        this.xp = xp;
        this.setChanged();
    }

    public void setXpCapacity(int xpCapacity) {
        this.xpCapacity = xpCapacity;
    }

    public void setDown() {
        CompoundNBT posNbt = new CompoundNBT();
        posNbt.putInt("posX", this.worldPosition.getX());
        posNbt.putInt("posY", this.worldPosition.getY());
        posNbt.putInt("posZ", this.worldPosition.getZ());
        MessageObeliskDown messageObeliskDownToServer = new MessageObeliskDown(posNbt);
        NetworkHandler.INSTANCE.sendToServer(messageObeliskDownToServer);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.setXp(nbt.getInt("xp"));
        this.setXpCapacity(nbt.getInt("xpCapacity"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("xp", this.getXp());
        nbt.putInt("xpCapacity", this.getXpCapacity());
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
    }
}
