package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.BlockRegistry;

public class TribalTorchDownBlock extends Block {
    public static final Properties properties = getProperties();

    private static final VoxelShape BASE_SHAPE = Block.box(7.0d, 0.0d, 7.0d, 9.0d, 16.0d, 9.0d);

    public TribalTorchDownBlock() {
        super(properties);
    }

    public static Properties getProperties()
    {
        return Properties
                .of()
                .strength(1f, 10f)
                .sound(SoundType.WOOD);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return BASE_SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (level.isEmptyBlock(pos.above()))
        {
            level.setBlock(pos, BlockRegistry.TRIBAL_TORCH_BLOCK.get().defaultBlockState(), 2);
        }

        if (level.isEmptyBlock(pos.below()))
        {
            level.destroyBlock(pos, true);
        }
    }
}
