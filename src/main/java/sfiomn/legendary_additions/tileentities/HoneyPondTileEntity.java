package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.HoneyPondBlock;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
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
        this.setHealingCapacity(Math.min(this.healingCapacity + addedCharges, MAX_HEALING_CAPACITY));
    }

    public void useOneHealingCharge() {
        this.setHealingCapacity(Math.max(this.healingCapacity - 1, 0));
    }

    public void setHealingCapacity(int healingCapacity) {
        this.healingCapacity = healingCapacity;
        if (this.level == null)
            return;
        int honeyPondState = 2;
        if (this.healingCapacity == 0) {
            honeyPondState = 0;
        } else if (this.healingCapacity <= ((float) MAX_HEALING_CAPACITY / 2.0f)) {
            honeyPondState = 1;
        }
        this.setChanged();
        this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(HoneyPondBlock.HONEY_POND_STATE, honeyPondState));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.setHealingCapacity(nbt.getInt("healingCapacity"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("healingCapacity", getHealingCapacity());
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
        nbt.putInt("healingCapacity", getHealingCapacity());
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.setHealingCapacity(nbt.getInt("healingCapacity"));
    }
}
