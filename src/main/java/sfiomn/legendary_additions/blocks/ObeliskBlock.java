package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.entities.ObeliskTileEntity;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;

public class ObeliskBlock extends Block {
    private static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP = Block.box(4.0D, 16.0D, 4.0D, 12.0D, 31.0D, 12.0D);
    public static final BooleanProperty OBELISK_EMPTY = BooleanProperty.create("obelisk_empty");
    private static final VoxelShape SHAPE = VoxelShapes.or(BASE, TOP);

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        return Properties
                .of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(50f, 1200f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .lightLevel((state) -> state.getValue(OBELISK_EMPTY) ? 0: 15)
                .noOcclusion();
    }
    public ObeliskBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(OBELISK_EMPTY, false));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(OBELISK_EMPTY);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                             BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);

        if (new Vector3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()))
            return ActionResultType.FAIL;

        int remainingXp = 0;
        int xpCapacity = 0;
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof ObeliskTileEntity) {
            remainingXp = ((ObeliskTileEntity) tileEntity).getXp();
            xpCapacity = ((ObeliskTileEntity) tileEntity).getXpCapacity();
            if (xpCapacity == 0) {
                ((ObeliskTileEntity) tileEntity).setXpCapacity(remainingXp);
                xpCapacity = remainingXp;
            }
        }

        int xpGiven = Math.min(xpCapacity / 10, remainingXp);
        remainingXp -= xpGiven;

        if (xpGiven > 0) {
            if (world instanceof ServerWorld)
                popExperience((ServerWorld) world, pos.offset(hit.getDirection().getNormal()), xpGiven);

            if (world instanceof ClientWorld) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
            }
        }

        if (xpGiven > 0) {
            ((ObeliskTileEntity) tileEntity).setXp(remainingXp);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof ObeliskTileEntity && world instanceof ServerWorld) {
                popExperience((ServerWorld) world, pos, ((ObeliskTileEntity) tileEntity).getXp());
            }
            if (tileEntity != null)
                tileEntity.setRemoved();
        }
        super.onRemove(blockState, world, pos, newBlockState, isMoving);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.OBELISK_TILE_ENTITY.get().create();
    }
}
