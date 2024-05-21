package sfiomn.legendary_additions.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.screens.ClientHooks;
import sfiomn.legendary_additions.tileentities.AbstractDungeonHeartTileEntity;

import java.util.List;
import java.util.Objects;

public abstract class AbstractDungeonHeartBlock extends Block {
    public static final BooleanProperty BASE = BooleanProperty.create("base");

    public AbstractDungeonHeartBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(BASE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(BASE);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        BlockPos basePos = getBasePos(state, pos);

        if (Objects.equals(player.getMainHandItem().getItem().getRegistryName(), this.getItemForDeactivation())) {
            TileEntity tileEntity = world.getBlockEntity(basePos);
            if (tileEntity instanceof AbstractDungeonHeartTileEntity) {
                AbstractDungeonHeartTileEntity te = (AbstractDungeonHeartTileEntity) tileEntity;
                if (te.isActive()) {
                    te.setActive(false);
                    player.getMainHandItem().shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        if (world.isClientSide && player.isCreative() && Minecraft.getInstance().screen == null) {
            TileEntity tileEntity = world.getBlockEntity(basePos);

            if (tileEntity instanceof AbstractDungeonHeartTileEntity) {
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openDungeonHeartScreen(tileEntity));
            } else {
                LegendaryAdditions.LOGGER.debug("Tile entity container is not correct at pos {}", basePos);
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean p_196243_5_) {
        super.onRemove(state, world, pos, newState, p_196243_5_);
        if (isBase(state)) {
            world.removeBlock(pos.above(), false);
        } else if (!isBase(state)) {
            world.destroyBlock(pos.below(), false);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighbor, boolean p_220069_6_) {
        super.neighborChanged(state, world, pos, block, neighbor, p_220069_6_);
        BlockPos basePos = getBasePos(state, pos);

        TileEntity tileEntity = world.getBlockEntity(basePos);
        if(!world.isClientSide() && tileEntity instanceof AbstractDungeonHeartTileEntity && canDeactivateBySignal()) {
            if (world.hasNeighborSignal(basePos))
                ((AbstractDungeonHeartTileEntity) tileEntity).setActive(false);
            else
                ((AbstractDungeonHeartTileEntity) tileEntity).setActive(true);
        }
    }

    abstract public TileEntity createTileEntity(BlockState state, IBlockReader world);

    @Override
    public boolean hasTileEntity(BlockState state) {
        return isBase(state);
    }

    public boolean isBase(BlockState state) {
        return state.getValue(BASE);
    }
    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        return isBase(state) ? pos : pos.below();
    }
    abstract public boolean canDrop();
    abstract public boolean isBreakable();
    abstract public boolean canDeactivateBySignal();
    abstract public ResourceLocation getItemForDeactivation();

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        List<ItemStack> lootList = super.getDrops(state, context);
        if (!isBase(state) || !canDrop())
            lootList.removeIf(e -> (e.getItem() instanceof BlockItem && ((BlockItem) e.getItem()).getBlock() instanceof AbstractDungeonHeartBlock));

        return lootList;
    }
}
