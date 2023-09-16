package sfiomn.legendary_additions.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import sfiomn.legendary_additions.entities.*;
import sfiomn.legendary_additions.tileentities.LegendaryXpBottleEntity;
import sfiomn.legendary_additions.util.XpBottleEnum;

public class XpBottleItem extends Item {

    private final XpBottleEnum bottleEntityType;
    public XpBottleItem(XpBottleEnum bottleEntityType, Properties properties) {
        super(properties);
        this.bottleEntityType = bottleEntityType;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
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
            xpBottleEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.7F, 1.0F);
            world.addFreshEntity(xpBottleEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }
}
