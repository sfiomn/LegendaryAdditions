package sfiomn.legendary_additions.blocks;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendary_additions.blockentities.MeatRackBlockEntity;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;
import sfiomn.legendary_additions.util.ModTags;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MeatRackBlock extends BaseEntityBlock {
    private static final VoxelShape Z_BASE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
    private static final VoxelShape X_BASE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
    public static final Properties properties = getProperties();
    public static final IntegerProperty MEAT_RACK_STATE = IntegerProperty.create("meat_rack_state", 0, 3);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static Properties getProperties() {
        return Properties
                .of()
                .mapColor(MapColor.WOOD)
                .sound(SoundType.WOOD)
                .strength(1f, 10f)
                .randomTicks()
                .noOcclusion();
    }

    public MeatRackBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(MEAT_RACK_STATE, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> entityType) {
        return level.isClientSide ? null : createTickerHelper(entityType, BlockEntityRegistry.MEAT_RACK_BLOCK_ENTITY.get(), MeatRackBlockEntity::serverTick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_BASE : Z_BASE;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MEAT_RACK_STATE);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        super.use(blockState, level, pos, player, hand, hit);

        int meatRackState = level.getBlockState(pos).getValue(MEAT_RACK_STATE);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MeatRackBlockEntity meatRackBlockEntity) {
            switch (meatRackState) {
                case 0:
                    if (player.getMainHandItem().is(ModTags.CAN_DRY)) {
                        meatRackState = 1;
                        player.getMainHandItem().shrink(1);
                        meatRackBlockEntity.storeMeat(player.getMainHandItem().getItem());

                        if (level instanceof ClientLevel) {
                            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                        }
                    }
                    break;
                case 1:
                    popResource(level, pos, meatRackBlockEntity.removeMeat());
                    if (level instanceof ClientLevel) {
                        level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_BREAK, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }
                    meatRackState = 0;
                    break;
                case 2:
                    popResource(level, pos, new ItemStack(Items.LEATHER));
                    if (level instanceof ClientLevel) {
                        level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_BREAK, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }
                    meatRackState = 0;
                    break;
                case 3:
                    popResource(level, pos, new ItemStack(Items.BONE));
                    if (level instanceof ClientLevel) {
                        level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }
                    meatRackState = 0;
                    break;
                default:
                    break;
            }

            updateBlockProperties(level, pos, meatRackState);
        }
        return InteractionResult.SUCCESS;
    }

    private void updateBlockProperties(Level level, BlockPos pos, int meatRackState) {
        level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(MEAT_RACK_STATE, meatRackState));
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newBlockState, boolean pMovedByPiston) {
        if (!blockState.is(newBlockState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MeatRackBlockEntity meatRackBlockEntity) {
                switch (blockState.getValue(MEAT_RACK_STATE)) {
                    case 1:
                        popResource(level, pos, meatRackBlockEntity.removeMeat());
                        break;
                    case 2:
                        popResource(level, pos, new ItemStack(Items.LEATHER));
                        break;
                    case 3:
                        popResource(level, pos, new ItemStack(Items.BONE));
                        break;
                    default:
                        break;
                }
            }
        }
        super.onRemove(blockState, level, pos, newBlockState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.MEAT_RACK_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return (BlockState)pState.setValue(FACING, pRot.rotate((Direction)pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }
}
