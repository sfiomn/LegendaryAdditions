package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TribalTorchWallBlock extends WallTorchBlock {
    public static final Properties properties = getProperties();

    public TribalTorchWallBlock() {
        super(properties, ParticleTypes.FLAME);
    }

    public static Properties getProperties()
    {
        return Properties
                .of()
                .noCollission()
                .strength(1f, 10f)
                .lightLevel((p_235470_0_) -> {
                    return 14;})
                .sound(SoundType.WOOD);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource rand) {
        Direction direction = blockState.getValue(FACING);
        double d0 = (double)blockPos.getX() + 0.5D;
        double d1 = (double)blockPos.getY() + 0.7D;
        double d2 = (double)blockPos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.15D;
        Direction direction1 = direction.getOpposite();
        level.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction1.getStepX(), d1 + d3, d2 + d4 * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
        level.addParticle(this.flameParticle, d0 + d4 * (double)direction1.getStepX(), d1 + d3, d2 + d4 * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
    }
}
