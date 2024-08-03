package sfiomn.legendary_additions.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import sfiomn.legendary_additions.config.Config;

public class HoneyPondItem extends BlockItem {
    public HoneyPondItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        int healingCapacity = 0;
        if (stack.getTag() != null)
            healingCapacity = getHealingCapacityFromTag(stack.getTag());
        if (healingCapacity == Config.Baked.honeyPondMaxCapacity)
            return Component.literal(super.getName(stack).getString()).append(" Full");
        else if (healingCapacity == 0)
            return Component.literal(super.getName(stack).getString()).append(" Empty");

        return super.getName(stack);
    }

    public static CompoundTag setHealingCapacityInTag(CompoundTag tag, int healingCapacity) {
        CompoundTag blockEntityTag = new CompoundTag();
        if (tag != null) {
            if (tag.contains("BlockEntityTag")) {
                blockEntityTag = tag.getCompound("BlockEntityTag");
            }
        } else {
            tag = new CompoundTag();
        }
        blockEntityTag.putInt("healingCapacity", healingCapacity);
        tag.put("BlockEntityTag", blockEntityTag);
        return tag;
    }

    private int getHealingCapacityFromTag(CompoundTag tag) {
        if (tag.contains("BlockEntityTag")) {
            return tag.getCompound("BlockEntityTag").getInt("healingCapacity");
        }
        return 0;
    }
}
