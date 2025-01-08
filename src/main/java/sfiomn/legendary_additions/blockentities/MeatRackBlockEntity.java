package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.blocks.MeatRackBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;


public class MeatRackBlockEntity extends BlockEntity {
    protected NonNullList<ItemStack> dryingMeat;
    private int rottingProgress;

    public MeatRackBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.MEAT_RACK_BLOCK_ENTITY.get(), blockPos, blockState);
        rottingProgress = 0;
        dryingMeat = NonNullList.withSize(1, ItemStack.EMPTY);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MeatRackBlockEntity entity) {
        if (level == null)
            return;

        int meatRackState = level.getBlockState(pos).getValue(MeatRackBlock.MEAT_RACK_STATE);
        if (meatRackState == 0 || meatRackState == 3) {
            entity.rottingProgress = 0;
        }

        if (meatRackState == 1 && entity.rottingProgress >= Config.Baked.meatRackLeatherTicks) {
            entity.setStateTo(2);
            entity.rottingProgress = 0;
        }

        if (meatRackState == 2 && entity.rottingProgress >= Config.Baked.meatRackBoneTicks) {
            entity.setStateTo(3);
            entity.rottingProgress = 0;
        }

        if (meatRackState == 1 || meatRackState == 2)
            entity.rottingProgress++;
    }

    public void storeMeat(Item meat) {
        dryingMeat.set(0, new ItemStack(meat));
    }

    public ItemStack removeMeat() {
        return dryingMeat.get(0).isEmpty() ? ItemStack.EMPTY : dryingMeat.get(0);
    }

    private void setStateTo(int state) {
        state = Mth.clamp(state, 0, 3);
        assert this.level != null;
        this.level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(MeatRackBlock.MEAT_RACK_STATE, state));
        this.setChanged();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.dryingMeat = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.dryingMeat);
        this.rottingProgress = tag.getInt("rottingProgress");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.dryingMeat);
        tag.putInt("rottingProgress", this.rottingProgress);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = this.getUpdateTag();
        ContainerHelper.saveAllItems(nbt, this.dryingMeat);
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.putInt("rottingProgress", this.rottingProgress);
        return nbt;
    }
}
