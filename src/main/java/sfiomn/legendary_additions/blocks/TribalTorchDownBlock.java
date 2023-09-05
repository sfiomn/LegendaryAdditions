package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class TribalTorchDownBlock extends Block {
    public static final Properties properties = getProperties();

    private static final VoxelShape BASE_SHAPE = Block.box(7.0d, 0.0d, 7.0d, 9.0d, 16.0d, 9.0d);

    public TribalTorchDownBlock() {
        super(properties);
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.DECORATION)
                .strength(1f, 10f)
                .harvestTool(ToolType.AXE)
                .sound(SoundType.WOOD);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return BASE_SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (world.isEmptyBlock(pos.above()))
        {
            world.setBlock(pos, BlockRegistry.TRIBAL_TORCH_BLOCK.get().defaultBlockState(), 2);
        }

        if (world.isEmptyBlock(pos.below()))
        {
            world.destroyBlock(pos, true);
        }

    }
}
