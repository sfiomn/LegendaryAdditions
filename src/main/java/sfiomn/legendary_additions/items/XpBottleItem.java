package sfiomn.legendary_additions.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.entities.*;
import sfiomn.legendary_additions.util.XpBottleEnum;

public class XpBottleItem extends Item {

    private final XpBottleEnum bottleEntityType;
    public XpBottleItem(XpBottleEnum bottleEntityType, Properties properties) {
        super(properties);
        this.bottleEntityType = bottleEntityType;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        world.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            XpBottleEntity xpBottleEntity = getXpBottleEntity(world, player, itemstack);
            xpBottleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.7F, 1.0F);
            world.addFreshEntity(xpBottleEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.isCreative()) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    private @NotNull XpBottleEntity getXpBottleEntity(Level world, Player player, ItemStack itemstack) {
        XpBottleEntity xpBottleEntity = new TinyXpBottleEntity(world, player);
        if (this.bottleEntityType == XpBottleEnum.COMMON) {
            xpBottleEntity = new CommonXpBottleEntity(world, player);
        } else if (this.bottleEntityType == XpBottleEnum.RARE) {
            xpBottleEntity = new RareXpBottleEntity(world, player);
        } else if (this.bottleEntityType == XpBottleEnum.EPIC) {
            xpBottleEntity = new EpicXpBottleEntity(world, player);
        } else if (this.bottleEntityType == XpBottleEnum.LEGENDARY) {
            xpBottleEntity = new LegendaryXpBottleEntity(world, player);
        }
        xpBottleEntity.setItem(itemstack);
        return xpBottleEntity;
    }
}
