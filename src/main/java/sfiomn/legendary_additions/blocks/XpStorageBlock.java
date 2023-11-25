package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.TileEntityRegistry;
import sfiomn.legendary_additions.tileentities.ObeliskTileEntity;
import sfiomn.legendary_additions.tileentities.XpStorageTileEntity;

import javax.annotation.Nullable;

public class XpStorageBlock extends Block {
    public static final Properties properties = getProperties();;
    public static final IntegerProperty STATE = IntegerProperty.create("xp_storage_state", 0, 3);

    public static Properties getProperties()
    {
        return Properties
                .of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(20f, 600f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .noOcclusion();
    }

    public XpStorageBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(STATE, 3));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(STATE);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }


    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);

        if (new Vector3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()))
            return ActionResultType.FAIL;

        if (player.experienceLevel == 0)
            return ActionResultType.PASS;

        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof XpStorageTileEntity) {

            XpStorageTileEntity xpStorageTileEntity = (XpStorageTileEntity) tileEntity;

            int maxXpCapacity = xpStorageTileEntity.getXpCapacity();
            if (maxXpCapacity == 0) {
                xpStorageTileEntity.setXpCapacity(Config.Baked.xpStorageMaxXpCapacity);
                maxXpCapacity = Config.Baked.xpStorageMaxXpCapacity;
            }

            if (xpStorageTileEntity.getXp() >= maxXpCapacity)
                return ActionResultType.PASS;

            if (world instanceof ClientWorld) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
            }

            int newPlayerLevel = player.experienceLevel - 1;

            int xpLevel = this.getXpNeededForNextLevel(newPlayerLevel);

            int xpStored;
            if (xpLevel > maxXpCapacity)
                xpStored = maxXpCapacity;
            else
                xpStored = Math.min(xpLevel, maxXpCapacity - xpStorageTileEntity.getXp());

            player.giveExperiencePoints(-xpStored);

            MinecraftForge.EVENT_BUS.post(new PlayerXpEvent.XpChange(player, -xpStored));

            xpStorageTileEntity.setXp(xpStorageTileEntity.getXp() + xpStored);

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    public int getXpNeededForNextLevel(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof XpStorageTileEntity && world instanceof ServerWorld) {
                popExperience((ServerWorld) world, pos, ((XpStorageTileEntity) tileEntity).getXp());
            }
            super.onRemove(blockState, world, pos, newBlockState, isMoving);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.XP_STORAGE_TILE_ENTITY.get().create();
    }
}
