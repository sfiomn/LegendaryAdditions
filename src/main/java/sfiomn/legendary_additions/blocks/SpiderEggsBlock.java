package sfiomn.legendary_additions.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.config.Config;

public class SpiderEggsBlock extends AbstractSpawnerBlock {

    public static Properties properties = getProperties();

    public static Properties getProperties()
    {
        Properties properties =
                Properties
                        .of(Material.WOOD)
                        .sound(SoundType.WOOD)
                        .strength(12f, 1200f)
                        .harvestTool(ToolType.AXE)
                        .harvestLevel(4)
                        .noOcclusion();

        if (!Config.Baked.forestDungeonHeartBreakable)
            properties.strength(-1.0f, 3600000.0F);

        return properties;
    }

    public SpiderEggsBlock() {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;
    }

    public boolean isBase(BlockState state) {
        return true;
    }

    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        return pos;
    }
}
