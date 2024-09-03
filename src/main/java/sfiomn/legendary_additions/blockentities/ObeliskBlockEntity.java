package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;


public class ObeliskBlockEntity extends BlockEntity {
    public int xpCapacity;
    private int xp;

    public ObeliskBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.OBELISK_BLOCK_ENTITY.get(), blockPos, blockState);
        this.xp = this.xpCapacity = 0;
    }

    public int getXp() {
        return this.xp;
    }

    public int getXpCapacity() {
        return this.xpCapacity;
    }

    public boolean isDown() {
        if (this.level != null && this.level.getBlockState(this.worldPosition).hasProperty(ObeliskBlock.OBELISK_DOWN))
            return this.level.getBlockState(this.worldPosition).getValue(ObeliskBlock.OBELISK_DOWN);
        return true;
    }

    public void setDown() {
        if (level != null && level.getBlockState(worldPosition).hasProperty(ObeliskBlock.OBELISK_DOWN))
            level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(ObeliskBlock.OBELISK_DOWN, true));
        if (level != null && level.getBlockState(worldPosition.above()).hasProperty(ObeliskBlock.OBELISK_DOWN))
            level.setBlockAndUpdate(worldPosition.above(), level.getBlockState(worldPosition.above()).setValue(ObeliskBlock.OBELISK_DOWN, true));
    }

    public void setXp(int xp) {
        this.xp = xp;
        this.setChanged();
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
        return nbt;
    }
}
