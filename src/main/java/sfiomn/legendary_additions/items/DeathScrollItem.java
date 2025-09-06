package sfiomn.legendary_additions.items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendary_additions.capabilities.death_position.DeathPositionCapability;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.util.CapabilityUtil;

import java.util.Collections;
import java.util.List;

public class DeathScrollItem extends Item {

    public DeathScrollItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 50;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
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
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int tick) {
        super.onUseTick(level, entity, stack, tick);
        if (level.isClientSide && tick == getUseDuration(stack) - 10)
            Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {

        if(!(entity instanceof Player player))
            return stack;

        DeathPositionCapability cap = CapabilityUtil.getDeathPositionCapability(player);

        if (cap.hasDeathPosition() && !cap.isPositionUsed()) {
            cap.setPositionUsed(true);

            if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer)
                teleport(serverLevel, serverPlayer, cap.getDeathPosition());
        }

        if (!player.isCreative())
            stack.shrink(1);

        player.getCooldowns().addCooldown(stack.getItem(), 60);

        return stack;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.translatable("tooltip.legendary_additions.death_scroll.description"));
    }

    private void teleport(ServerLevel level, ServerPlayer player, BlockPos targetPos) {
        float yRot = player.getYRot();
        double x = targetPos.getX() + 0.5;
        double y = targetPos.getY() + 0.5;
        double z = targetPos.getZ() + 0.5;

        ChunkPos chunkPos = new ChunkPos(targetPos);
        level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, player.getId());
        player.unRide();

        player.connection.teleport(x, y, z, yRot, player.getXRot(), Collections.emptySet());

        player.setYHeadRot(yRot);
    }
}
