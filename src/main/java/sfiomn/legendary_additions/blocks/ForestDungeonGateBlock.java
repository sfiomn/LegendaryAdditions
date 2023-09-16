package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.TileEntityRegistry;
import sfiomn.legendary_additions.util.DungeonGatePartTypeEnum;
import sfiomn.legendary_additions.util.IDungeonGatePart;

import javax.annotation.Nullable;
import java.util.Objects;

public class ForestDungeonGateBlock extends DungeonGateBlock {

    public static final Properties properties = getProperties();
    public static final EnumProperty<ForestDungeonGatePart> PART = EnumProperty.create("part", ForestDungeonGatePart.class);
    private static final double shapeDepth = 8.0D;
    private static final VoxelShape SHAPE_Z_AXIS = Block.box(0.0D, 0.0D, (16 - shapeDepth) * 0.5, 16.0D, 16.0D, ((16 - shapeDepth) * 0.5 + shapeDepth));
    private static final VoxelShape SHAPE_X_AXIS = Block.box((16 - shapeDepth) * 0.5, 0.0D, 0.0D, ((16 - shapeDepth) * 0.5 + shapeDepth), 16.0D, 16.0D);
    private static final VoxelShape[] OPEN_SHAPES = new VoxelShape[]
            {
                    Block.box(0.0d, shapeDepth, 0.0d, 16.0d, 16.0d, 16.0d), // DOWN
                    Block.box(0.0d, 0.0d, 0.0d, 16.0d, shapeDepth, 16.0d), // UP
                    Block.box(0.0D, 0.0D, shapeDepth, 16.0D, 16.0D, 16.0D), // NORTH
                    Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, shapeDepth), // SOUTH
                    Block.box(shapeDepth, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), // WEST
                    Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, shapeDepth), // EAST
            };
    public static Properties getProperties()
    {

        Properties properties = Properties
                .of(Material.WOOD)
                .sound(SoundType.STONE)
                .strength(50f, 100f)
                .harvestTool(ToolType.AXE)
                .harvestLevel(4)
                .noOcclusion();

        if (Config.Baked.forestDungeonGateUnbreakable)
            properties.strength(-1.0f, 3600000.0F);
        return properties;
    }

    public ForestDungeonGateBlock() {
        super(properties, ForestDungeonGatePart.values());

        this.defaultBlockState().setValue(PART, ForestDungeonGatePart.GATEWAY_0);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        if (!state.getValue(OPENED))
            return state.getValue(FACING).getAxis() == Direction.Axis.X ? SHAPE_X_AXIS : SHAPE_Z_AXIS;
        else {
            IDungeonGatePart part = getDungeonPart(state);
            if (part.isGateHingeRight()) {
                Direction gateFacing = state.getValue(FACING);
                Vector3d.atLowerCornerOf(gateFacing.getNormal()).scale((16 - shapeDepth) * 0.5);
                return OPEN_SHAPES[this.dungeonGatePartUtil.getOpenDirection(part, gateFacing).get3DDataValue()].move();
            } else if (part.isGateHingeLeft()) {
                Direction gateFacing = state.getValue(FACING);
                return OPEN_SHAPES[this.dungeonGatePartUtil.getOpenDirection(part, gateFacing).get3DDataValue()];
            } else if (part.isOpenLeft()) {
                return OPEN_SHAPES[state.getValue(FACING).get3DDataValue()];
            } else if (part.isOpenRight()) {
                return OPEN_SHAPES[state.getValue(FACING).get3DDataValue()];
            } else
                return VoxelShapes.empty();
        }
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, player, itemStack);
        if (!world.isClientSide) {
            for (IDungeonGatePart part : this.dungeonGatePartUtil.getCloseParts()) {
                if (!part.isBase() && part instanceof ForestDungeonGatePart) {
                    ForestDungeonGatePart forestPart = (ForestDungeonGatePart) part;
                    world.setBlockAndUpdate(pos.offset(part.offset(state.getValue(FACING))), state.setValue(PART, forestPart));
                }
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.FOREST_DUNGEON_GATE_TILE_ENTITY.get().create();
    }

    @Override
    IDungeonGatePart getDungeonPart(BlockState blockState) {
        return blockState.hasProperty(PART) ? blockState.getValue(PART) : null;
    }

    @Override
    public double getDepth() {
        return ForestDungeonGateBlock.shapeDepth / 16.0;
    }

    @Override
    public boolean canDropKeys() {
        return Config.Baked.forestDungeonGateDropKeys;
    }

    @Override
    public boolean canDropGate() {
        return Config.Baked.forestDungeonGateDrop;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    public enum ForestDungeonGatePart implements IDungeonGatePart {
        GATEWAY_0("gateway_0", DungeonGatePartTypeEnum.GATEWAY, new Vector3i(0, 0, 0)),
        GATEWAY_1("gateway_1", DungeonGatePartTypeEnum.GATEWAY, new Vector3i(0, 1, 0)),
        GATEWAY_2("gateway_2", DungeonGatePartTypeEnum.GATEWAY, new Vector3i(0, 2, 0)),
        LEFT_0("hinge_left_0", DungeonGatePartTypeEnum.HINGE_LEFT, new Vector3i(-1, 0, 0)),
        LEFT_1("hinge_left_1", DungeonGatePartTypeEnum.HINGE_LEFT, new Vector3i(-1, 1, 0)),
        LEFT_2("hinge_left_2", DungeonGatePartTypeEnum.HINGE_LEFT, new Vector3i(-1, 2, 0)),
        OPEN_LEFT_0("open_left_0", DungeonGatePartTypeEnum.OPEN_GATEWAY, new Vector3i(-1, 0, 1)),
        OPEN_LEFT_1("open_left_1", DungeonGatePartTypeEnum.OPEN_GATEWAY, new Vector3i(-1, 1, 1)),
        OPEN_LEFT_2("open_left_2", DungeonGatePartTypeEnum.OPEN_GATEWAY, new Vector3i(-1, 2, 1));

        private final String name;
        private final DungeonGatePartTypeEnum partType;
        private final Vector3i offset;

        // By default: offset x+1 = right block, offset x-1 = left block
        ForestDungeonGatePart(String name, DungeonGatePartTypeEnum partType, Vector3i offset) {
            this.name = name;
            this.partType = partType;
            this.offset = offset;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public Vector3i offset() {
            return this.offset;
        }

        @Override
        public DungeonGatePartTypeEnum partType() {
            return this.partType;
        }

        @Override
        public boolean isBase() {
            return Objects.equals(this.getSerializedName(), "gateway_0");
        }
    }
}
