package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.entities.SeatEntity;
import sfiomn.legendary_additions.registry.BlockRegistry;

import java.util.List;

public class CaptainChairBlock extends HorizontalBlock {

    public static final Properties properties = getProperties();

    public CaptainChairBlock() {
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult traceResult)
    {
        return SeatEntity.create(world, pos, 0.4, playerEntity, state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        World level = context.getLevel();
        BlockPos topPos = context.getClickedPos().above();
        return level.getBlockState(topPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(topPos)
                ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                : null;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (world.getBlockState(pos.above()).getBlock() != BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get())
        {
            world.destroyBlock(pos, true);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (world.isEmptyBlock(pos.above()))
        {
            world.setBlock(pos.above(), BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get().defaultBlockState().setValue(CaptainChairTopBlock.FACING, state.getValue(FACING)), 2);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState stateIn, boolean p_196243_5_) {
        super.onRemove(state, world, pos, stateIn, p_196243_5_);
        if(!world.isClientSide())
        {
            List<SeatEntity> seats = world.getEntitiesOfClass(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            for (SeatEntity seat: seats) {
                seat.remove();
            }
        }
    }
}
