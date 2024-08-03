package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class MossBlock extends WoolCarpetBlock {

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        return Properties
                .of()
                .mapColor(MapColor.GRASS)
                .sound(SoundType.GRASS)
                .strength(0.1F)
                .noLootTable()
                .noOcclusion();
    }

    public MossBlock() {
        super(DyeColor.GREEN, properties);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader world, BlockPos pos) {
        return super.canSurvive(blockState, world, pos) && world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP);
    }
}
