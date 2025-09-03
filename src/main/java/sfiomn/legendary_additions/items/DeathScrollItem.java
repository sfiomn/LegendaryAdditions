package sfiomn.legendary_additions.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionCapability;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.util.CapabilityUtil;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

import java.util.List;

public class DeathScrollItem extends Item {

    public DeathScrollItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (Config.Baked.deathScrollEnabled) {

            DeathPositionCapability cap = CapabilityUtil.getDeathPositionCapability(player);
            if (cap.isPositionUsed()) {
                if (level.isClientSide)
                    player.displayClientMessage(Component.translatable("message.legendary_additions.death_scroll.already_used"), true);

            } else if (!cap.hasDeathPosition()) {
                if (level.isClientSide)
                    player.displayClientMessage(Component.translatable("message.legendary_additions.death_scroll.no_position"), true);

            } else {
                player.startUsingItem(hand);
                return InteractionResultHolder.success(stack);
            }
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {

        if(!(entity instanceof Player player))
            return stack;

        if (!level.isClientSide) {
            DeathPositionCapability cap = CapabilityUtil.getDeathPositionCapability(player);

            if (cap.hasDeathPosition() && !cap.isPositionUsed()) {
                cap.setPositionUsed(true);

                player.unRide();

                BlockPos deathPosition = cap.getDeathPosition();
                Vec3 pos = new Vec3(deathPosition.getX() + 0.5, deathPosition.getY() + 0.5, deathPosition.getZ() + 0.5);
                player.moveTo(pos);
            }
        }

        if (!player.isCreative())
            stack.shrink(1);
        return stack;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("tooltip.legendary_additions.death_scroll.description"));
    }
}
