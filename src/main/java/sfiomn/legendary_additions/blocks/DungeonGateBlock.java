package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.tileentities.AbstractDungeonGateTileEntity;
import sfiomn.legendary_additions.util.DungeonGatePartUtil;
import sfiomn.legendary_additions.util.IDungeonGatePart;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class DungeonGateBlock extends HorizontalBlock implements IWaterLoggable {
    public static final BooleanProperty OPENED = BooleanProperty.create("opened");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final DungeonGatePartUtil dungeonGatePartUtil;

    public DungeonGateBlock(AbstractBlock.Properties properties, IDungeonGatePart[] dungeonGateParts) {
        super(properties);

        this.dungeonGatePartUtil = new DungeonGatePartUtil(dungeonGateParts);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(OPENED, false)
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(FACING, Direction.SOUTH));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(OPENED, WATERLOGGED, FACING);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        int gateHeight = this.dungeonGatePartUtil.getHeight();
        return (blockpos.getY() + gateHeight) < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) ?
                this.defaultBlockState()
                        .setValue(FACING, context.getHorizontalDirection().getOpposite())
                        .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER) : null;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                             BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);

        if (new Vector3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()))
            return ActionResultType.FAIL;

        IDungeonGatePart dungeonGatePart = this.getDungeonPart(blockstate);
        if (dungeonGatePart == null)
            return ActionResultType.FAIL;

        // retrieve the tile entity that controls the DungeonGate
        TileEntity tileEntity = world.getBlockEntity(getBasePos(blockstate, pos));
        if (tileEntity instanceof AbstractDungeonGateTileEntity) {
            AbstractDungeonGateTileEntity dungeonGateTE = (AbstractDungeonGateTileEntity) tileEntity;
            Vector3i facingVector = blockstate.getValue(FACING).getNormal();
            Vector3d hitLocationCenterGate = hit.getLocation().subtract(facingVector.getX() * getDepth() / 2, 0, facingVector.getZ() * getDepth() / 2);
            dungeonGateTE.insertKey(player, hitLocationCenterGate);
            if (dungeonGateTE.isUnlocked()) {
                if (!dungeonGateTE.isOpened()) {
                    if (canOpen(blockstate, world, pos)) {
                        dungeonGateTE.openGate();
                    }
                } else
                    dungeonGateTE.closeGate();
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockStateIn, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (!this.canSurvive(blockState, world, pos)) {
            this.removeGate(world, pos, blockState, null);
            return Blocks.AIR.defaultBlockState();
        }

        return blockState;
    }

    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.removeGate(world, pos, state, player);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof AbstractDungeonGateTileEntity && canDropKeys()) {
                ((AbstractDungeonGateTileEntity) tileEntity).dropKeys();
            }
        }
        super.onRemove(blockState, world, pos, newBlockState, isMoving);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        if (!getDungeonPart(state).isBase() || !canDropGate())
            return Collections.emptyList();
        return super.getDrops(state, context);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return this.getDungeonPart(state).isBase();
    }
/*
    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos basePos = getBasePos(state, pos);

        for (IDungeonGatePart part: this.dungeonGatePartUtil.getNeighbourOffsets(getDungeonPart(state))) {
            BlockPos partPos = basePos.offset(part.offset(state.getValue(FACING)));
            BlockState partState = world.getBlockState(partPos);
            if (!partState.is(this))
                return false;
        }

        return true;
    }*/

    public boolean canOpen(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos basePos = getBasePos(state, pos);

        for (IDungeonGatePart part: this.dungeonGatePartUtil.getOpenParts()) {
            BlockPos partPos = basePos.offset(part.offset(state.getValue(FACING)));
            BlockState partState = world.getBlockState(partPos);
            if (!partState.getMaterial().isReplaceable())
                return false;
        }

        return true;
    }

    public void removeGate(IWorld world, BlockPos pos, BlockState state, @Nullable PlayerEntity player) {
        if (world.isClientSide() || (player != null && !player.isCreative())) {
            return;
        }

        BlockPos basePos = getBasePos(state, pos);
        List<IDungeonGatePart> partsToRemove = this.dungeonGatePartUtil.getCloseParts();
        if (state.getValue(OPENED))
            partsToRemove = this.dungeonGatePartUtil.dungeonGateParts();

        for (IDungeonGatePart part: partsToRemove) {
            BlockPos partPos = basePos.offset(part.offset(state.getValue(FACING)));
            BlockState partState = world.getBlockState(partPos);
            if (partState.getBlock().is(this)) {
                if (part.isBase()) {
                    world.destroyBlock(partPos, true, player);
                } else {
                    world.setBlock(partPos, partState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                    world.levelEvent(player, 2001, partPos, Block.getId(partState));
                }
            }
        }
    }

    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        IDungeonGatePart part = getDungeonPart(state);
        if (part.isBase())
            return pos;
        else
            return pos.offset(getDungeonPart(state).reverseOffset(state.getValue(FACING)));
    }

    abstract public TileEntity createTileEntity(BlockState state, IBlockReader world);

    abstract IDungeonGatePart getDungeonPart(BlockState blockState);

    abstract public double getDepth();

    abstract public boolean canDropKeys();
    abstract public boolean canDropGate();
}
