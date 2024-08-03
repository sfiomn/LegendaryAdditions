package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.blocks.HoneyPondBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;


public class HoneyPondBlockEntity extends BlockEntity {
    private int healingCapacity;

    public HoneyPondBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.HONEY_POND_BLOCK_ENTITY.get(), blockPos, blockState);
        this.healingCapacity = 0;
    }

    public int getHealingCapacity() {
        return this.healingCapacity;
    }

    public void addHealingCharges(int addedCharges) {
        this.setHealingCapacity(Math.min(this.healingCapacity + addedCharges, Config.Baked.honeyPondMaxCapacity));
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
        } else if (this.healingCapacity <= ((float) Config.Baked.honeyPondMaxCapacity / 2.0f)) {
            honeyPondState = 1;
        }
        this.setChanged();
        this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(HoneyPondBlock.HONEY_POND_STATE, honeyPondState));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.setHealingCapacity(tag.getInt("healingCapacity"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("healingCapacity", getHealingCapacity());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.putInt("healingCapacity", getHealingCapacity());
        return nbt;
    }
}
