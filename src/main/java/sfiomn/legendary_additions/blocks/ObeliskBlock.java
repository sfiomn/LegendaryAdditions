package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
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
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.tileentities.ObeliskTileEntity;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

public class ObeliskBlock extends Block {
    private static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_BASE = Block.box(4.0D, 16.0D, 4.0D, 12.0D, 31.0D, 12.0D);
    private static final VoxelShape TOP_DOWN_BASE = Block.box(4.0D, 16.0D, 4.0D, 12.0D, 27.0D, 12.0D);
    private static final VoxelShape TOP = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    private static final VoxelShape BASE_TOP = Block.box(0.0D, 0.0D, 0.0D, 16.0D, -16.0D, 16.0D);
    private static final VoxelShape TOP_DOWN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);
    private static final VoxelShape SHAPE_BASE = VoxelShapes.or(BASE, TOP_BASE);
    private static final VoxelShape SHAPE_BASE_DOWN = VoxelShapes.or(BASE, TOP_DOWN_BASE);
    private static final VoxelShape SHAPE_TOP = VoxelShapes.or(BASE_TOP, TOP);
    private static final VoxelShape SHAPE_TOP_DOWN = VoxelShapes.or(BASE_TOP, TOP_DOWN);
    public static final BooleanProperty OBELISK_DOWN = BooleanProperty.create("obelisk_down");
    public static final EnumProperty<ObeliskPart> PART = EnumProperty.create("part", ObeliskPart.class);

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        Properties properties = Properties
                .of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(12f, 100f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .lightLevel((state) -> state.getValue(OBELISK_DOWN) ? 0: 15)
                .noOcclusion();

        if (!Config.Baked.obeliskBreakable)
            properties.strength(50f, 1200f);

        return properties;
    }

    public ObeliskBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(OBELISK_DOWN, false).setValue(PART, ObeliskPart.BASE));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.getValue(PART).isBase() ? state.getValue(OBELISK_DOWN) ? SHAPE_BASE_DOWN : SHAPE_BASE : state.getValue(OBELISK_DOWN) ? SHAPE_TOP_DOWN : SHAPE_TOP;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(OBELISK_DOWN, PART);
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

        BlockPos basePos = getBasePos(blockstate, pos);

        int remainingXp = 0;
        int xpCapacity = 0;
        TileEntity tileEntity = world.getBlockEntity(basePos);
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (!canPlace(context))
            return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, player, itemStack);
        world.setBlockAndUpdate(pos.above(), state.setValue(PART, ObeliskPart.TOP));
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            if (blockState.getValue(PART).isBase()) {
                TileEntity tileEntity = world.getBlockEntity(pos);
                if (tileEntity instanceof ObeliskTileEntity && world instanceof ServerWorld) {
                    popExperience((ServerWorld) world, pos, ((ObeliskTileEntity) tileEntity).getXp());
                }
            }
        }
        super.onRemove(blockState, world, pos, newBlockState, isMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        // if the block is base obelisk, the change comes from above, and above is no longer an obelisk, remove the block
        if (state.getValue(PART).isBase() && !worldIn.getBlockState(pos.above()).is(BlockRegistry.OBELISK_BLOCK.get()))
        {
            worldIn.removeBlock(pos, false);
        } else if (!state.getValue(PART).isBase() && !worldIn.getBlockState(pos.below()).is(BlockRegistry.OBELISK_BLOCK.get())) {
            worldIn.removeBlock(pos, false);
        }
    }

    public boolean isBase(BlockState state) {
        return state.getValue(PART).isBase();
    }
    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        return isBase(state) ? pos : pos.below();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(PART).isBase();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.OBELISK_TILE_ENTITY.get().create();
    }

    private boolean canPlace(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (pos.getY() > world.getHeight() - 2) {
            return false;
        }

        if (world.getBlockState(pos.above()).isSolidRender(world, pos.above())) {
            return false;
        }

        return true;
    }

    public enum ObeliskPart implements IStringSerializable {
        BASE("base"),
        TOP("top");

        private String name;

        ObeliskPart(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public boolean isBase() {
            return Objects.equals(this.name, "base");
        }
    }
}
