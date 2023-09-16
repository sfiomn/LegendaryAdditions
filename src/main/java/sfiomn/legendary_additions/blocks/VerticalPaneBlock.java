package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.Map;


public class VerticalPaneBlock extends PaneBlock implements IWaterLoggable {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = SixWayBlock.PROPERTY_BY_DIRECTION;
    public VerticalPaneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    public BlockState getStateForPlacement(IBlockReader blockReader, BlockPos pos) {
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    public final boolean canAttachTo(BlockState blockState, boolean isFaceSturdy) {
        Block block = blockState.getBlock();
        return !isExceptionForConnection(block) && isFaceSturdy || block instanceof PaneBlock || block.is(BlockTags.WALLS) || block instanceof VerticalBlock;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateIn, IWorld world, BlockPos pos, BlockPos posIn) {
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        // If update coming from horizontal, just do legacy Panel check
        if (direction.getAxis().isHorizontal()) {
            return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction),
                    this.attachsTo(blockStateIn, blockStateIn.isFaceSturdy(world, posIn, direction.getOpposite())));
        }

        // If update coming from vertical, checks if it's a Vertical Panel
        return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction),
                blockStateIn.getBlock() == this);
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
