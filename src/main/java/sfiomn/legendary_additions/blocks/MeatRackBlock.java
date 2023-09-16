package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MeatRackBlock extends HorizontalBlock {
    private static final VoxelShape Z_BASE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
    private static final VoxelShape X_BASE = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
    public static final Properties properties = getProperties();
    public static final IntegerProperty MEAT_RACK_STATE = IntegerProperty.create("meat_rack_state", 0, 3);

    public static Properties getProperties() {
        return Properties
                .of(Material.WOOD)
                .sound(SoundType.WOOD)
                .strength(1f, 10f)
                .harvestTool(ToolType.AXE)
                .harvestLevel(4)
                .randomTicks()
                .noOcclusion();
    }

    public MeatRackBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(MEAT_RACK_STATE, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_BASE : Z_BASE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, MEAT_RACK_STATE);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                BlockRayTraceResult hit) {
        super.use(blockState, world, pos, player, hand, hit);

        int meatRackState = world.getBlockState(pos).getValue(MEAT_RACK_STATE);

        switch (meatRackState) {
            case 0:
                if (player.getMainHandItem().getItem() == Items.ROTTEN_FLESH) {
                    meatRackState = 1;
                    player.getMainHandItem().shrink(1);
                    if (world instanceof ClientWorld) {
                        world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                    }
                }
                break;
            case 1:
                popResource(world, pos, new ItemStack(Items.ROTTEN_FLESH));
                if (world instanceof ClientWorld) {
                    world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_BREAK, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                }
                meatRackState = 0;
                break;
            case 2:
                popResource(world, pos, new ItemStack(Items.LEATHER));
                if (world instanceof ClientWorld) {
                    world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_BREAK, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                }
                meatRackState = 0;
                break;
            case 3:
                popResource(world, pos, new ItemStack(Items.BONE));
                if (world instanceof ClientWorld) {
                    world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                }
                meatRackState = 0;
                break;
            default:
                break;
        }

        updateBlockProperties(world, pos, meatRackState);
        return ActionResultType.SUCCESS;
    }

    private void updateBlockProperties(World world, BlockPos pos, int meatRackState) {
        world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(MEAT_RACK_STATE, meatRackState));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);

        switch (state.getValue(MEAT_RACK_STATE)) {
            case 1:
                dropsOriginal.add(new ItemStack(Items.ROTTEN_FLESH));
                break;
            case 2:
                dropsOriginal.add(new ItemStack(Items.LEATHER));
                break;
            case 3:
                dropsOriginal.add(new ItemStack(Items.BONE));
                break;
            default:
                break;
        }

        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.MEAT_RACK_TILE_ENTITY.get().create();
    }
}
