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

public class ObeliskItem extends BlockItem {
    public ObeliskItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> stacks) {
        super.fillItemCategory(itemGroup, stacks);
        CompoundNBT tag;

        if (this.category == itemGroup) {
            for (int xp: Config.Baked.obeliskXpValues) {
                ItemStack stack = new ItemStack(this);
                tag = setXpInTag(stack.getTag(), xp);
                stack.setTag(tag);
                stacks.add(stack);
            }
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        int xp = 0;
        if (stack.getTag() != null)
            xp = getXpFromTag(stack.getTag());
        return new StringTextComponent(super.getName(stack).getString()).append(" XP " + xp);
    }

    public CompoundNBT setXpInTag(CompoundNBT tag, int xp) {
        CompoundNBT blockEntityTag = new CompoundNBT();
        if (tag != null) {
            if (tag.contains("BlockEntityTag")) {
                blockEntityTag = tag.getCompound("BlockEntityTag");
            }
        } else {
            tag = new CompoundNBT();
        }
        blockEntityTag.putInt("xp", xp);
        tag.put("BlockEntityTag", blockEntityTag);
        return tag;
    }

    private int getXpFromTag(CompoundNBT tag) {
        if (tag.contains("BlockEntityTag")) {
            return tag.getCompound("BlockEntityTag").getInt("xp");
        }
        return 0;
    }
}
