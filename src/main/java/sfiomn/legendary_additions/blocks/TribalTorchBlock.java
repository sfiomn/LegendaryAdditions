package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;

public class TribalTorchBlock extends TorchBlock {
    public static final Properties properties = getProperties();

    private static final VoxelShape BASE_SHAPE = Block.box(5.5d, 0.0d, 5.5d, 10.5d, 16.0d, 10.5d);
    public TribalTorchBlock() {
        super(properties, ParticleTypes.FLAME);
    }

    public static Properties getProperties()
    {
        return Properties
                .of()
                .strength(1f, 10f)
                .lightLevel((p_235470_0_) -> {
                    return 14;})
                .sound(SoundType.WOOD);
    }


    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        super.use(blockstate, level, pos, player, hand, hit);

        if (player.getItemInHand(hand).getItem() == ItemRegistry.TRIBAL_TORCH.get()) {
            if (level.isEmptyBlock(pos.above()))
            {
                level.setBlockAndUpdate(pos.above(), BlockRegistry.TRIBAL_TORCH_BLOCK.get().defaultBlockState());
                level.setBlockAndUpdate(pos, BlockRegistry.TRIBAL_TORCH_DOWN_BLOCK.get().defaultBlockState());
                if (level.isClientSide)
                    level.playLocalSound(pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
                if (!player.isCreative())
                    player.getItemInHand(hand).shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
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
        if (level.isEmptyBlock(pos.below()))
        {
            level.destroyBlock(pos, true);
        }
    }
}
