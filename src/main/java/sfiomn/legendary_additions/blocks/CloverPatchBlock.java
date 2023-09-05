package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolType;


public class CloverPatchBlock extends FlowerBlock implements IPlantable {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D);

    public static final Properties properties = getProperties();
    public static final Effect effect = Effects.LUCK;
    public CloverPatchBlock() {
        super(effect, 1, properties);
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.PLANT)
                .sound(SoundType.GRASS)
                .noCollission()
                .instabreak();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(reader, pos);
        return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
        return blockState.is(Blocks.GRASS_BLOCK) || blockState.is(Blocks.DIRT) || blockState.is(Blocks.COARSE_DIRT) || blockState.is(Blocks.PODZOL) || blockState.is(Blocks.FARMLAND);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return defaultBlockState();
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }
}
