package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.tileentities.AbstractGateTileEntity;
import sfiomn.legendary_additions.util.GatePartUtil;
import sfiomn.legendary_additions.util.IGatePart;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractGateBlock extends HorizontalBlock implements IWaterLoggable {
    public static final BooleanProperty OPENED = BooleanProperty.create("opened");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final GatePartUtil gatePartUtil;

    public AbstractGateBlock(AbstractBlock.Properties properties, IGatePart[] gateParts) {
        super(properties);

        this.gatePartUtil = new GatePartUtil(gateParts);

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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (!canPlace(context))
            return null;

        BlockPos blockpos = context.getClickedPos();
        World world = context.getLevel();
        FluidState fluidState = world.getFluidState(blockpos);
        int gateHeight = this.gatePartUtil.getHeight();
        return (blockpos.getY() + gateHeight) < 255 ?
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

        IGatePart gatePart = this.getGatePart(blockstate);
        if (gatePart == null)
            return ActionResultType.FAIL;

        // retrieve the tile entity that controls the DungeonGate
        TileEntity tileEntity = world.getBlockEntity(getBasePos(blockstate, pos));
        if (tileEntity instanceof AbstractGateTileEntity) {
            AbstractGateTileEntity dungeonGateTE = (AbstractGateTileEntity) tileEntity;

            if (player.isCreative() && player.getMainHandItem().getItem() instanceof SpawnEggItem) {
                SpawnEggItem eggInHand = (SpawnEggItem) player.getMainHandItem().getItem();
                dungeonGateTE.setEntityLock(eggInHand.getType(player.getMainHandItem().getTag()));
                player.getMainHandItem().shrink(1);

                if (world.isClientSide && dungeonGateTE.getEntityLock().isPresent())
                    player.displayClientMessage(new TranslationTextComponent("block.legendary_additions.gate.entityLockApplied", Objects.requireNonNull(dungeonGateTE.getEntityLock().get().getRegistryName()).getPath()), true);

                return ActionResultType.SUCCESS;
            }
            Direction gateFacing = blockstate.getValue(FACING);
            Direction insertFacing = gateFacing.getAxis() == Direction.Axis.X ?
                    (hit.getLocation().x - Math.floor(hit.getLocation().x)) >= 0.5 ? Direction.WEST : Direction.EAST:
                    (hit.getLocation().z - Math.floor(hit.getLocation().z)) >= 0.5 ? Direction.NORTH : Direction.SOUTH;
            boolean keyInserted = dungeonGateTE.insertKey(player, hit.getLocation(), insertFacing);
            if (dungeonGateTE.isUnlocked() && !dungeonGateTE.isOpened()) {
                dungeonGateTE.openGate();
            } else if (dungeonGateTE.isOpened() && canClose()) {
                dungeonGateTE.closeGate();
            } else if (!world.isClientSide && !keyInserted) {
                dungeonGateTE.playFailedToOpenSound();
                if (!dungeonGateTE.checkEntityUnlocked() && dungeonGateTE.getEntityLock().isPresent())
                    player.displayClientMessage(new TranslationTextComponent("block.legendary_additions.gate.entityLocked", Objects.requireNonNull(dungeonGateTE.getEntityLock().get().getRegistryName()).getPath()), true);
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
            this.removeGate(world, pos, blockState);
            return Blocks.AIR.defaultBlockState();
        }

        return blockState;
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            BlockPos basePos = getBasePos(blockState, pos);
            TileEntity tileentity = world.getBlockEntity(basePos);
            if (tileentity instanceof AbstractGateTileEntity) {
                if (((AbstractGateTileEntity) tileentity).isOpened() || !isOpenPart(blockState)) {
                    this.removeGate(world, pos, blockState);
                    ((AbstractGateTileEntity) tileentity).dropKeys();
                }
                super.onRemove(blockState, world, pos, newBlockState, isMoving);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        List<ItemStack> lootList = super.getDrops(state, context);
        if (!getGatePart(state).isBase() || !canDropGate())
            lootList.removeIf(e -> (e.getItem() instanceof BlockItem && ((BlockItem) e.getItem()).getBlock() instanceof AbstractGateBlock));

        return lootList;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return this.getGatePart(state).isBase();
    }

    public boolean canPlace(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos basePos = context.getClickedPos();
        Direction gateFacing = context.getHorizontalDirection().getOpposite();
        for (IGatePart part: this.gatePartUtil.getClosedGateParts()) {
            try {
                BlockPos partPos = basePos.offset(part.offset(gateFacing));
                BlockState partState = world.getBlockState(partPos);
                if (!partState.canBeReplaced(context)) {
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }

        return true;
    }

    public void removeGate(IWorld world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) {
            return;
        }

        BlockPos basePos = getBasePos(state, pos);
        List<IGatePart> partsToRemove = this.gatePartUtil.getClosedGateParts();
        if (state.getValue(OPENED))
            partsToRemove = this.gatePartUtil.gateParts();

        for (IGatePart part: partsToRemove) {
            BlockPos partPos = basePos.offset(part.offset(state.getValue(FACING)));
            BlockState partState = world.getBlockState(partPos);
            if (partState.getBlock().is(this)) {
                if (part.isBase()) {
                    world.destroyBlock(partPos, true);
                } else {
                    world.setBlock(partPos, partState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                    world.levelEvent(2001, partPos, Block.getId(partState));
                }
            }
        }
    }

    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        IGatePart part = getGatePart(state);
        if (part.isBase())
            return pos;
        else
            return pos.offset(getGatePart(state).reverseOffset(state.getValue(FACING)));
    }

    abstract public TileEntity createTileEntity(BlockState state, IBlockReader world);

    abstract IGatePart getGatePart(BlockState blockState);

    abstract public boolean canClose();

    abstract public boolean isBreakable();
    abstract public boolean canDropGate();

    abstract public boolean isOpenPart(BlockState state);
}
