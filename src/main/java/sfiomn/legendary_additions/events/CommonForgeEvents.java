package sfiomn.legendary_additions.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.blocks.ObeliskBlock;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.SoundRegistry;

@Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onBreakingBlockIronOnCoal(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();

        if (Config.Baked.ironOnCoalExplosionEnabled) {
            BlockState blockState = event.getLevel().getBlockState(event.getPos());
            if (player.getMainHandItem().getItem() == Items.IRON_PICKAXE && blockState.is(Blocks.COAL_ORE)) {
                if (player.level().isClientSide && player.level().getGameTime() % 10 == 0) {
                    Direction direction = event.getFace();
                    if (direction != null) {
                        Vec3i directionOrthogonal = new Vec3i(1 - Math.abs(direction.getStepX()), 1 - Math.abs(direction.getStepY()), 1 - Math.abs(direction.getStepZ()));
                        double offsetX = ((player.level().random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getX() + 0.55 * direction.getStepX();
                        double offsetY = ((player.level().random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getY() + 0.55 * direction.getStepY();
                        double offsetZ = ((player.level().random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getZ() + 0.55 * direction.getStepZ();
                        // Center + offset based on face currently mine
                        double x = event.getPos().getX() + 0.5 + offsetX;
                        double y = event.getPos().getY() + 0.5 + offsetY;
                        double z = event.getPos().getZ() + 0.5 + offsetZ;
                        if (player.level().random.nextFloat() < 0.5)
                            player.level().addParticle(ParticleTypes.FLAME, false, x, y, z, 0.05 * direction.getStepX(), 0.03, 0.05 * direction.getStepZ());
                        else
                            player.level().addParticle(ParticleTypes.FIREWORK, false, x, y, z, 0.05 * direction.getStepX(), 0.03, 0.05 * direction.getStepZ());
                        player.level().playLocalSound(x, y, z, SoundRegistry.IRON_ON_COAL.get(), SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
            }
        }

        BlockPos basePos = null;
        BlockPos pos = event.getPos();
        if (!player.isCreative() && event.getLevel().isClientSide) {
            Block clickedBlock = event.getLevel().getBlockState(event.getPos()).getBlock();
            if (clickedBlock instanceof ObeliskBlock && Config.Baked.obeliskBreakable) {
                BlockState state = event.getLevel().getBlockState(event.getPos());
                basePos = ((ObeliskBlock) state.getBlock()).getBasePos(state, pos);
            }
        }

        if (basePos != null) {
            if (basePos != pos && Minecraft.getInstance().gameMode != null) {
                event.setCanceled(true);
                MultiPlayerGameMode playerController = Minecraft.getInstance().gameMode;
                if (!playerController.isDestroying()) {
                    playerController.startDestroyBlock(basePos, player.getDirection());
                } else {
                    Minecraft.getInstance().gameMode.continueDestroyBlock(basePos, player.getDirection());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!Config.Baked.ironOnCoalExplosionEnabled)
            return;

        Player player = event.getPlayer();
        LevelAccessor level = event.getLevel();
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        if (player.getMainHandItem().getItem() == Items.IRON_PICKAXE && blockState.is(Blocks.COAL_ORE)) {
            if (level.getRandom().nextFloat() < Config.Baked.ironOnCoalExplosionChance) {
                if (!level.isClientSide()) {
                    if (level instanceof Level) {
                        ((Level) level).explode(null, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 4.0f, Level.ExplosionInteraction.BLOCK);
                    }
                }
            }
        }
    }
}
