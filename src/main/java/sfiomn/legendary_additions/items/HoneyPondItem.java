package sfiomn.legendary_additions.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import sfiomn.legendary_additions.config.Config;

public class HoneyPondItem extends BlockItem {
    public HoneyPondItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> stacks) {
        super.fillItemCategory(itemGroup, stacks);
        CompoundNBT tag;

        if (this.category == itemGroup) {
            ItemStack stackFull = new ItemStack(this);
            tag = setHealingCapacityInTag(stackFull.getTag(), Config.Baked.honeyPondMaxCapacity);
            stackFull.setTag(tag);
            stacks.add(stackFull);
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        int healingCapacity = 0;
        if (stack.getTag() != null)
            healingCapacity = getHealingCapacityFromTag(stack.getTag());
        if (healingCapacity == Config.Baked.honeyPondMaxCapacity)
            return new StringTextComponent(super.getName(stack).getString()).append(" Full");
        else if (healingCapacity == 0)
            return new StringTextComponent(super.getName(stack).getString()).append(" Empty");

        return super.getName(stack);
    }

    public CompoundNBT setHealingCapacityInTag(CompoundNBT tag, int healingCapacity) {
        CompoundNBT blockEntityTag = new CompoundNBT();
        if (tag != null) {
            if (tag.contains("BlockEntityTag")) {
                blockEntityTag = tag.getCompound("BlockEntityTag");
            }
        } else {
            tag = new CompoundNBT();
        }
        blockEntityTag.putInt("healingCapacity", healingCapacity);
        tag.put("BlockEntityTag", blockEntityTag);
        return tag;
    }

    private int getHealingCapacityFromTag(CompoundNBT tag) {
        if (tag.contains("BlockEntityTag")) {
            return tag.getCompound("BlockEntityTag").getInt("healingCapacity");
        }
        return 0;
    }
}
