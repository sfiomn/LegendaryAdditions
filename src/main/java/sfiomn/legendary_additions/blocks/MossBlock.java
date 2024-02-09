package sfiomn.legendary_additions.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class MossBlock extends CarpetBlock {

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        return Properties
                .of(Material.GRASS)
                .sound(SoundType.GRASS)
                .strength(0.1F)
                .harvestTool(ToolType.SHOVEL)
                .noOcclusion();
    }

    public MossBlock() {
        super(DyeColor.GREEN, properties);
    }

    @Override
    public boolean canSurvive(BlockState blockState, IWorldReader world, BlockPos pos) {
        return super.canSurvive(blockState, world, pos) && world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP);
    }
}
