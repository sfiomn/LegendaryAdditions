package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class CaptainChairTopBlock extends HorizontalBlock {

    public static final Properties properties = getProperties();
    private static final VoxelShape NORTH_SHAPE_CHAIR_BACK = Block.box(0.0d, 0.0d, 13.0d, 16.0d, 16.0d, 16.0d);
    private static final VoxelShape SOUTH_SHAPE_CHAIR_BACK = Block.box(0.0d, 0.0d, 0.0d, 16.0d, 16.0d, 3.0d);
    private static final VoxelShape WEST_SHAPE_CHAIR_BACK = Block.box(13.0d, 0.0d, 0.0d, 16.0d, 16.0d, 16.0d);
    private static final VoxelShape EAST_SHAPE_CHAIR_BACK = Block.box(0.0d, 0.0d, 0.0d, 3.0d, 16.0d, 16.0d);

    public CaptainChairTopBlock() {
        super(properties);
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.WOOD)
                .sound(SoundType.WOOD)
                .strength(1f, 10f)
                .harvestTool(ToolType.AXE)
                .noOcclusion();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        Direction direction = state.getValue(FACING);
        switch(direction) {
            case NORTH:
                return NORTH_SHAPE_CHAIR_BACK;
            case SOUTH:
                return SOUTH_SHAPE_CHAIR_BACK;
            case WEST:
                return WEST_SHAPE_CHAIR_BACK;
            default:
                return EAST_SHAPE_CHAIR_BACK;
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (world.getBlockState(pos.below()).getBlock() != BlockRegistry.CAPTAIN_CHAIR_BLOCK.get())
        {
            world.destroyBlock(pos, true);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
