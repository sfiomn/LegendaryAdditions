package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Map;


public class VerticalPaneBlock extends CrossCollisionBlock {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    public VerticalPaneBlock(BlockBehaviour.Properties properties) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    public BlockState getStateForPlacement(BlockGetter blockReader, BlockPos pos) {
        FluidState fluidstate = blockReader.getFluidState(pos);
        Block blockDown = blockReader.getBlockState(pos.below()).getBlock();
        Block blockUp = blockReader.getBlockState(pos.above()).getBlock();
        BlockState blockstateNorth = blockReader.getBlockState(pos.north());
        BlockState blockstateSouth = blockReader.getBlockState(pos.south());
        BlockState blockstateEast = blockReader.getBlockState(pos.east());
        BlockState blockstateWest = blockReader.getBlockState(pos.west());
        return this.defaultBlockState()
                .setValue(DOWN, blockDown == this)
                .setValue(UP, blockUp == this)
                .setValue(NORTH, this.canAttachTo(blockstateNorth, blockstateNorth.isFaceSturdy(blockReader, pos.north(), Direction.SOUTH)))
                .setValue(EAST, this.canAttachTo(blockstateEast, blockstateEast.isFaceSturdy(blockReader, pos.east(), Direction.WEST)))
                .setValue(SOUTH, this.canAttachTo(blockstateSouth, blockstateSouth.isFaceSturdy(blockReader, pos.south(), Direction.NORTH)))
                .setValue(WEST, this.canAttachTo(blockstateWest, blockstateWest.isFaceSturdy(blockReader, pos.west(), Direction.EAST)))
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    public final boolean canAttachTo(BlockState blockState, boolean isFaceSturdy) {
        Block block = blockState.getBlock();
        return (!isExceptionForConnection(blockState) && isFaceSturdy) || block instanceof CrossCollisionBlock || blockState.is(BlockTags.WALLS);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateIn, LevelAccessor level, BlockPos pos, BlockPos posIn) {
        if (blockState.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        // If update coming from horizontal, just do legacy Panel check
        if (direction.getAxis().isHorizontal()) {
            return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction),
                    this.canAttachTo(blockStateIn, blockStateIn.isFaceSturdy(level, posIn, direction.getOpposite())));
        }

        // If update coming from vertical, checks if it's a Vertical Panel
        return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction),
                blockStateIn.getBlock() == this);
    }
}
