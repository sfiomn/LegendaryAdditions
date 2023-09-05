package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.ItemRegistry;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;

public class TribalTorchBlock extends TorchBlock {
    public static final Properties properties = getProperties();

    private static final VoxelShape BASE_SHAPE = Block.box(5.5d, 0.0d, 5.5d, 10.5d, 16.0d, 10.5d);
    public TribalTorchBlock() {
        super(properties, ParticleTypes.FLAME);
    }

    public static Properties getProperties()
    {
        return Properties
                .of(Material.DECORATION)
                .strength(1f, 10f)
                .harvestTool(ToolType.AXE)
                .lightLevel((p_235470_0_) -> {
                    return 14;})
                .sound(SoundType.WOOD);
    }


    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);

        if (player.getItemInHand(hand).getItem() == ItemRegistry.TRIBAL_TORCH.get()) {
            if (world.isEmptyBlock(pos.above()))
            {
                world.setBlock(pos.above(), BlockRegistry.TRIBAL_TORCH_BLOCK.get().defaultBlockState(), 2);
                world.setBlock(pos, BlockRegistry.TRIBAL_TORCH_DOWN_BLOCK.get().defaultBlockState(), 2);
                player.getItemInHand(hand).shrink(1);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return BASE_SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (world.isEmptyBlock(pos.below()))
        {
            world.destroyBlock(pos, true);
        }
    }
}
