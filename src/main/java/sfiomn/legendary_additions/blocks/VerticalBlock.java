package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ToolType;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.Map;


public class VerticalBlock extends AbstractGlassBlock {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = SixWayBlock.PROPERTY_BY_DIRECTION;

    public VerticalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE));
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2f, 50f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .noOcclusion();
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    public BlockState getStateForPlacement(IBlockReader blockReader, BlockPos pos) {
        Block blockDown = blockReader.getBlockState(pos.below()).getBlock();
        Block blockUp = blockReader.getBlockState(pos.above()).getBlock();
        return this.defaultBlockState()
                .setValue(DOWN, blockDown == this)
                .setValue(UP, blockUp == this);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateIn, IWorld world, BlockPos pos, BlockPos posIn) {

        // If update coming from vertical, check if it's the same block
        if (direction.getAxis().isVertical()) {
            return blockState.setValue(PROPERTY_BY_DIRECTION.get(direction),
                    blockStateIn.getBlock() == this);
        }
        return blockState;
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
