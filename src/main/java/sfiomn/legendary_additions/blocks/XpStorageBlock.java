package sfiomn.legendary_additions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;

public class XpStorageBlock extends Block {
    public static final int MAX_XP_CAPACITY = Config.Baked.xpStorageMaxXpCapacity;
    public static final Properties properties = getProperties();
    public static final IntegerProperty XP_CAPACITY = IntegerProperty.create("xp_capacity", 0, MAX_XP_CAPACITY);
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 2);

    public static Properties getProperties()
    {
        return Properties
                .of(Material.GLASS)
                .sound(SoundType.GLASS)
                .strength(50f, 1200f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .noOcclusion();
    }

    public XpStorageBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(XP_CAPACITY, 0)
                .setValue(STATE, 2));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }


    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);
        int xpCapacity = world.getBlockState(pos).getValue(XP_CAPACITY);
        int xpGiven = 0;

        LegendaryAdditions.LOGGER.debug("player crouching while using obelisk: " + player.isCrouching());
        if (player.isCrouching()) {
            if (world instanceof ClientWorld) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
            }

            xpGiven = Math.min(player.totalExperience, Math.min(MAX_XP_CAPACITY / 10, MAX_XP_CAPACITY - xpCapacity));

            xpCapacity += xpGiven;
            player.giveExperiencePoints(-xpGiven);

        } else {
            if (world instanceof ClientWorld) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
            }

            xpGiven = Math.min(MAX_XP_CAPACITY / 10, xpCapacity);

            xpCapacity -= xpGiven;

            if (world instanceof ServerWorld)
                popExperience((ServerWorld) world, pos, xpGiven);
        }
        if (xpGiven > 0) {
            updateBlockProperties(world, pos, xpCapacity);
        }
        return ActionResultType.SUCCESS;
    }

    private void updateBlockProperties(World world, BlockPos pos, int newXpCapacity) {
        int state = 2;
        if (newXpCapacity == 0) {
            state = 0;
        } else if (newXpCapacity <= ((float) MAX_XP_CAPACITY / 2.0f)) {
            state = 1;
        }
        world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(XP_CAPACITY, newXpCapacity).setValue(STATE, state));
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            int xp_stored = blockState.getValue(XP_CAPACITY);

            if (world instanceof ServerWorld)
                popExperience((ServerWorld) world, pos, xp_stored);
            super.onRemove(blockState, world, pos, newBlockState, isMoving);
        }
    }
}
