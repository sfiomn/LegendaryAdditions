package sfiomn.legendary_additions.world.gen.features;

public class GlowingBulbFeature extends DefaultFlowersFeature {
    public YuccaFlowerFeature(Codec<BlockClusterFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        BlockState blockstate = AtmosphericBlocks.YUCCA_FLOWER.get().defaultBlockState();
        BlockState tallBlockstate = AtmosphericBlocks.TALL_YUCCA_FLOWER.get().defaultBlockState();
        int i = 0;

        for (int j = 0; j < this.getCount(config); ++j) {
            BlockPos blockpos = this.getPos(rand, pos, config);
            if (worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() - 1 && blockstate.canSurvive(worldIn, blockpos) && this.isValid(worldIn, blockpos, config)) {
                if (rand.nextBoolean() && worldIn.isEmptyBlock(blockpos.above())) {
                    worldIn.setBlock(blockpos, tallBlockstate, 2);
                    worldIn.setBlock(blockpos.above(), tallBlockstate.setValue(YuccaFlowerDoubleBlock.HALF, DoubleBlockHalf.UPPER), 2);
                } else {
                    worldIn.setBlock(blockpos, blockstate, 2);
                }
                ++i;
            }
        }
        return i > 0;
    }
}
