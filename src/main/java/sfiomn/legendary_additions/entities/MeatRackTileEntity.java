package sfiomn.legendary_additions.entities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.MeatRackBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;

public class MeatRackTileEntity extends TileEntity implements ITickableTileEntity {

    public static final int ROTTING_TICKS_BEFORE_LEATHER = Config.Baked.meatRackLeatherTicks;
    public static final int ROTTING_TICKS_BEFORE_BONE = Config.Baked.meatRackBoneTicks;

    private int rottingProgress;

    public MeatRackTileEntity() {
        super(TileEntityRegistry.MEAT_RACK_TILE_ENTITY.get());
        rottingProgress = 0;
    }

    @Override
    public void tick() {
        if (this.level == null)
            return;

        int meatRackState = this.level.getBlockState(this.worldPosition).getValue(MeatRackBlock.MEAT_RACK_STATE);
        if (meatRackState == 0 || meatRackState == 3) {
            rottingProgress = 0;
        }

        if (meatRackState == 1 && rottingProgress >= ROTTING_TICKS_BEFORE_LEATHER) {
            setStateTo(2);
            rottingProgress = 0;
        }

        if (meatRackState == 2 && rottingProgress >= ROTTING_TICKS_BEFORE_BONE) {
            setStateTo(3);
            rottingProgress = 0;
        }

        if (meatRackState == 1 || meatRackState == 2)
            rottingProgress++;
    }

    private void setStateTo(int state) {
        state = MathHelper.clamp(state, 0, 3);
        assert this.level != null;
        this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(MeatRackBlock.MEAT_RACK_STATE, state));
        this.setChanged();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.rottingProgress = nbt.getInt("rottingProgress");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("rottingProgress", this.rottingProgress);
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
        nbt.putInt("rottingProgress", this.rottingProgress);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.rottingProgress = nbt.getInt("rottingProgress");
    }
}
