package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.entities.SeatEntity;
import sfiomn.legendary_additions.registry.BlockRegistry;

import java.util.List;

public class CaptainChairBlock extends HorizontalDirectionalBlock {

    public static final Properties properties = getProperties();

    public CaptainChairBlock() {
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult traceResult)
    {
        return SeatEntity.create(level, pos, 0.4, playerEntity, state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos topPos = context.getClickedPos().above();
        return level.getBlockState(topPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(topPos)
                ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (level.isEmptyBlock(pos.above()))
        {
            level.setBlock(pos.above(), BlockRegistry.CAPTAIN_CHAIR_TOP_BLOCK.get().defaultBlockState().setValue(CaptainChairTopBlock.FACING, state.getValue(FACING)), 2);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState stateIn, boolean p_196243_5_) {
        super.onRemove(state, level, pos, stateIn, p_196243_5_);
        if(!level.isClientSide()) {
            List<SeatEntity> seats = level.getEntitiesOfClass(SeatEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            for (SeatEntity seat: seats) {
                seat.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        if(!state.is(state.getBlock()) && level.getBlockState(pos.above()).getBlock() instanceof CaptainChairTopBlock) {
            level.removeBlock(pos.above(), false);
        }
    }
}
