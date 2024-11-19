package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sfiomn.legendary_additions.registry.ParticleTypeRegistry;

public class PoisonGasBlock extends AirBlock {

    public PoisonGasBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.removeBlock(pos, false);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_53483_) {
        super.onPlace(state, level, pos, newState, p_53483_);
        level.scheduleTick(pos, this, getPoisonGasTickLife(level.random));
    }

    private int getPoisonGasTickLife(RandomSource rand) {
        return 40 + rand.nextInt(20);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 2));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        float chance_poison_smoke = 0.15f;

        //  Middle of the block
        double posX = pos.getX();
        double posY = pos.getY();
        double posZ = pos.getZ();

        if (rand.nextFloat() < chance_poison_smoke) {
            //  Particle spawns around the center of the block : [0.33 - 0.66]
            float zr = rand.nextFloat() * 0.7f + 0.15f;
            float yr = rand.nextFloat() * 0.7f + 0.15f;
            level.addParticle(ParticleTypeRegistry.POISON_SMOKE.get(), false, posX + 0.15, posY + yr, posZ + zr, 0, 0, 0);
        }
        if (rand.nextFloat() < chance_poison_smoke) {
            //  Particle spawns around the center of the block : [0.33 - 0.66]
            float zr = rand.nextFloat() * 0.7f + 0.15f;
            float yr = rand.nextFloat() * 0.7f + 0.15f;
            level.addParticle(ParticleTypeRegistry.POISON_SMOKE.get(), false, posX + 0.85, posY + yr, posZ + zr, 0, 0, 0);
        }
        if (rand.nextFloat() < chance_poison_smoke) {
            //  Particle spawns around the center of the block : [0.33 - 0.66]
            float xr = rand.nextFloat() * 0.7f + 0.15f;
            float yr = rand.nextFloat() * 0.7f + 0.15f;
            level.addParticle(ParticleTypeRegistry.POISON_SMOKE.get(), false, posX + xr, posY + yr, posZ + 0.15, 0, 0, 0);
        }
        if (rand.nextFloat() < chance_poison_smoke) {
            //  Particle spawns around the center of the block : [0.33 - 0.66]
            float xr = rand.nextFloat() * 0.7f + 0.15f;
            float yr = rand.nextFloat() * 0.7f + 0.15f;
            level.addParticle(ParticleTypeRegistry.POISON_SMOKE.get(), false, posX + xr, posY + yr, posZ + 0.85, 0, 0, 0);
        }

        super.animateTick(state, level, pos, rand);
    }
}
