package sfiomn.legendary_additions.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ObeliskItem extends BlockItem {
    public ObeliskItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        int xp = 0;
        if (stack.getTag() != null)
            xp = getXpFromTag(stack.getTag());
        return Component.literal(super.getName(stack).getString()).append(" XP " + xp);
    }

    public static CompoundTag setXpInTag(CompoundTag tag, int xp) {
        CompoundTag blockEntityTag = new CompoundTag();
        if (tag != null) {
            if (tag.contains(BLOCK_ENTITY_TAG)) {
                blockEntityTag = tag.getCompound(BLOCK_ENTITY_TAG);
            }
        } else {
            tag = new CompoundTag();
        }
        blockEntityTag.putInt("xp", xp);
        blockEntityTag.putInt("xpCapacity", xp);
        tag.put(BLOCK_ENTITY_TAG, blockEntityTag);
        return tag;
    }

    private int getXpFromTag(CompoundTag tag) {
        if (tag.contains(BLOCK_ENTITY_TAG)) {
            return tag.getCompound(BLOCK_ENTITY_TAG).getInt("xp");
        }
        return 0;
    }
}
