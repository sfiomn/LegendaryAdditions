package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CaptainChairTopBlock extends HorizontalDirectionalBlock {

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
                .of()
                .mapColor(MapColor.WOOD)
                .sound(SoundType.WOOD)
                .strength(1f, 10f)
                .noOcclusion();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH -> NORTH_SHAPE_CHAIR_BACK;
            case SOUTH -> SOUTH_SHAPE_CHAIR_BACK;
            case WEST -> WEST_SHAPE_CHAIR_BACK;
            default -> EAST_SHAPE_CHAIR_BACK;
        };
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_196243_5_) {
        super.onRemove(state, level, pos, newState, p_196243_5_);
        if(!state.is(newState.getBlock()) && level.getBlockState(pos.below()).getBlock() instanceof CaptainChairBlock)
        {
            level.removeBlock(pos.below(), false);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
