package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.blocks.XpStorageBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;


public class XpStorageBlockEntity extends BlockEntity {
    public int xpCapacity;
    private int xp;
    public XpStorageBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.XP_STORAGE_BLOCK_ENTITY.get(), blockPos, blockState);
        this.xp = 0;
        this.xpCapacity = Config.Baked.xpStorageMaxXpCapacity;
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
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.setXp(tag.getInt("xp"));
        this.setXpCapacity(tag.getInt("xpCapacity"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("xp", this.getXp());
        tag.putInt("xpCapacity", this.getXpCapacity());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.putInt("xp", this.getXp());
        nbt.putInt("xpCapacity", this.getXpCapacity());
        return nbt;
    }
}
