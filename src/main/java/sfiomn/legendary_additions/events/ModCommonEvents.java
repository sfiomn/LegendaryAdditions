package sfiomn.legendary_additions.events;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.SoundRegistry;

@Mod.EventBusSubscriber(modid = LegendaryAdditions.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommonEvents {

    @SubscribeEvent
    public static void onBreakingBlockIronOnCoal(PlayerInteractEvent.LeftClickBlock event) {
        if (!Config.Baked.ironOnCoalExplosionEnabled)
            return;
        PlayerEntity player = event.getPlayer();
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        if (player.getMainHandItem().getItem() == Items.IRON_PICKAXE && blockState.is(Blocks.COAL_ORE)) {
            if (player.level.isClientSide && player.level.getGameTime() % 10 == 0) {
                Direction direction = event.getFace();
                if (direction != null) {
                    Vector3i directionOrthogonal = new Vector3i(1 - Math.abs(direction.getStepX()), 1 - Math.abs(direction.getStepY()), 1 - Math.abs(direction.getStepZ()));
                    double offsetX = ((player.level.random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getX() + 0.55 * direction.getStepX();
                    double offsetY = ((player.level.random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getY() + 0.55 * direction.getStepY();
                    double offsetZ = ((player.level.random.nextFloat() * 2) - 1) * 0.3 * directionOrthogonal.getZ() + 0.55 * direction.getStepZ();
                    // Center + offset based on face currently mine
                    double x = event.getPos().getX() + 0.5 + offsetX;
                    double y = event.getPos().getY() + 0.5 + offsetY;
                    double z = event.getPos().getZ() + 0.5 + offsetZ;
                    if (player.level.random.nextFloat() < 0.5)
                        player.level.addParticle(ParticleTypes.FLAME, false, x, y, z,0.05 * direction.getStepX(),0.03,0.05 * direction.getStepZ());
                    else
                        player.level.addParticle(ParticleTypes.FIREWORK, false, x, y, z,0.05 * direction.getStepX(),0.03,0.05 * direction.getStepZ());
                    player.level.playLocalSound(x, y, z, SoundRegistry.IRON_ON_COAL.get(), SoundCategory.NEUTRAL, 1, 1, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreakBlockIronOnCoal(BlockEvent.BreakEvent event) {
        if (!Config.Baked.ironOnCoalExplosionEnabled)
            return;
        PlayerEntity player = event.getPlayer();
        IWorld world = event.getWorld();
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        if (player.getMainHandItem().getItem() == Items.IRON_PICKAXE && blockState.is(Blocks.COAL_ORE)) {
            if (world.getRandom().nextFloat() < Config.Baked.ironOnCoalExplosionChance) {
                if (!world.isClientSide()) {
                    if (world instanceof World) {
                        ((World) world).explode(null, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 4.0f, Explosion.Mode.BREAK);
                    }
                }
            }
        }
    }
}
