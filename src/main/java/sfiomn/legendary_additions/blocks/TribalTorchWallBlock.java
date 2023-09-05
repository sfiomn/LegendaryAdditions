package sfiomn.legendary_additions.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class TribalTorchWallBlock extends WallTorchBlock {
    public static final Properties properties = getProperties();

    public TribalTorchWallBlock() {
        super(properties, ParticleTypes.FLAME);
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.DECORATION)
                .strength(1f, 10f)
                .harvestTool(ToolType.AXE)
                .lightLevel((p_235470_0_) -> {
                    return 14;})
                .sound(SoundType.WOOD);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        Direction direction = p_180655_1_.getValue(FACING);
        double d0 = (double)p_180655_3_.getX() + 0.5D;
        double d1 = (double)p_180655_3_.getY() + 0.7D;
        double d2 = (double)p_180655_3_.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.15D;
        Direction direction1 = direction.getOpposite();
        p_180655_2_.addParticle(ParticleTypes.SMOKE, d0 + d4 * (double)direction1.getStepX(), d1 + d3, d2 + d4 * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
        p_180655_2_.addParticle(this.flameParticle, d0 + d4 * (double)direction1.getStepX(), d1 + d3, d2 + d4 * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
    }
}
