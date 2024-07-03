package sfiomn.legendary_additions.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.registry.BlockRegistry;

import java.util.Random;

public class GlowingBulbFeature extends DefaultFlowersFeature {
    public GlowingBulbFeature(Codec<BlockClusterFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState blockstate = BlockRegistry.GLOWING_BULB_BLOCK.get().defaultBlockState();
        int i = 0;
        LegendaryAdditions.LOGGER.debug("Try place the plant");

        for (int j = 0; j < this.getCount(config); ++j) {
            LegendaryAdditions.LOGGER.debug("tries : " + j);
            LegendaryAdditions.LOGGER.debug("initial pos place : " + pos);
            BlockPos blockpos = this.getPos(rand, pos, config);
            blockpos = worldIn.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG, blockpos);
            if ((worldIn.isEmptyBlock(blockpos) || config.canReplace && worldIn.getBlockState(blockpos).getMaterial().isReplaceable()) && blockpos.getY() < worldIn.getMaxBuildHeight() - 1 && blockstate.canSurvive(worldIn, blockpos) && this.isValid(worldIn, blockpos, config)) {
                if (worldIn.isEmptyBlock(blockpos.above())) {
                    worldIn.setBlock(blockpos, blockstate, 2);
                    worldIn.setBlock(blockpos.above(), blockstate.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 2);
                }
                ++i;
            }
        }
        return i > 0;
    }
}