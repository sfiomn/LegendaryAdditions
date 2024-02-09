package sfiomn.legendary_additions.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;

public class ForestDungeonHeartBlock extends AbstractDungeonHeartBlock {

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

    public ForestDungeonHeartBlock() {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (world.isEmptyBlock(pos.above()) && isBase(state))
            world.setBlockAndUpdate(pos.above(), state.setValue(AbstractDungeonHeartBlock.BASE, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (!canPlace(context))
            return null;
        return super.getStateForPlacement(context);
    }

    private boolean canPlace(BlockItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (pos.getY() > world.getHeight() - 2) {
            return false;
        }

        if (world.getBlockState(pos.above()).isSolidRender(world, pos.above())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canDrop() {
        return Config.Baked.forestDungeonHeartDrop;
    }

    @Override
    public boolean isBreakable() {
        return Config.Baked.forestDungeonHeartBreakable;
    }

    @Override
    public boolean canDeactivateBySignal() {
        return Config.Baked.forestDungeonHeartDeactivationByRedStone;
    }

    @Override
    public ResourceLocation getItemForDeactivation() {
        return new ResourceLocation(Config.Baked.forestDungeonHeartDeactivationByItem);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.FOREST_DUNGEON_HEART_TILE_ENTITY.get().create();
    }
}
