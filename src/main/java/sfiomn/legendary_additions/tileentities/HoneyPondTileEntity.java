package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.HoneyPondBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;

public class HoneyPondTileEntity extends TileEntity {
    public static final int MAX_HEALING_CAPACITY = Config.Baked.honeyPondMaxCapacity;
    private int healingCapacity;

    public HoneyPondTileEntity() {
        super(TileEntityRegistry.HONEY_POND_TILE_ENTITY.get());
        this.healingCapacity = 0;
    }

    public int getHealingCapacity() {
        return this.healingCapacity;
    }

    public void addHealingCharges(int addedCharges) {
        this.healingCapacity = Math.min(this.healingCapacity + addedCharges, MAX_HEALING_CAPACITY);
        updateState();
        this.setChanged();
    }

    public void useOneHealingCharge() {
        this.healingCapacity = Math.max(this.healingCapacity - 1, 0);
        updateState();
        this.setChanged();
    }

    private void updateState() {
        int state = getBlockState().getValue(HoneyPondBlock.HONEY_POND_STATE);
        int newState = 2;
        assert this.level != null;
        if (this.healingCapacity == 0) {
            newState = 0;
        }else if (this.healingCapacity <= ((float) MAX_HEALING_CAPACITY / 2.0f)) {
            newState = 1;
        }
        if (state != newState) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(HoneyPondBlock.HONEY_POND_STATE, newState));
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.healingCapacity = nbt.getInt("healingCapacity");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("healingCapacity", this.healingCapacity);
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
        nbt.putInt("healingCapacity", this.healingCapacity);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.healingCapacity = nbt.getInt("healingCapacity");
    }
}
