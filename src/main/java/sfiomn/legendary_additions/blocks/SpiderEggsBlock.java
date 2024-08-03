package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nullable;

public class SpiderEggsBlock extends AbstractSpawnerBlock {

    public static Properties properties = getProperties();

    public static Properties getProperties()
    {
        Properties properties =
                Properties
                        .of()
                        .mapColor(MapColor.WOOD)
                        .sound(SoundType.WOOD)
                        .strength(12f, 1200f)
                        .noLootTable()
                        .noOcclusion();

        return properties;
    }

    public SpiderEggsBlock() {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public boolean isBase(BlockState state) {
        return true;
    }

    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        return pos;
    }
}
