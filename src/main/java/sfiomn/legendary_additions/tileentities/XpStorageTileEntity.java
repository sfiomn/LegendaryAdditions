package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.XpStorageBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;

public class XpStorageTileEntity extends TileEntity {
    public int xpCapacity;
    private int xp;
    public XpStorageTileEntity() {
        super(TileEntityRegistry.XP_STORAGE_TILE_ENTITY.get());
        this.xp = this.xpCapacity = 0;
    }

    public int getXp() {
        return this.xp;
    }

    public int getXpCapacity() {
        return this.xpCapacity;
    }

    public void setXp(int xp) {
        this.xp = xp;
        this.setChanged();

        if (this.level == null)
            return;

        if (this.xpCapacity == 0)
            this.setXpCapacity(Config.Baked.xpStorageMaxXpCapacity);

        int state = 3;
        if (xp == 0)
            state = 0;
        else if (xp < this.xpCapacity / 2)
            state = 1;
        else if (xp < this.xpCapacity)
            state = 2;

        this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(XpStorageBlock.STATE, state));
    }

    public void setXpCapacity(int xpCapacity) {
        this.xpCapacity = xpCapacity;
        this.setChanged();
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
        nbt.putInt("xp", this.getXp());
        nbt.putInt("xpCapacity", this.getXpCapacity());
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.setXp(nbt.getInt("xp"));
        this.setXpCapacity(nbt.getInt("xpCapacity"));
    }
}
