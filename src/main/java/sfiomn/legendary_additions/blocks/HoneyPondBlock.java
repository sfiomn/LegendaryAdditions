package sfiomn.legendary_additions.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.blockentities.HoneyPondBlockEntity;
import sfiomn.legendary_additions.integration.legendarysurvivaloverhaul.LegendarySurvivalOverhaulUtil;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class HoneyPondBlock extends BaseEntityBlock {

    public static final Properties properties = getProperties();
    public static final IntegerProperty HONEY_POND_STATE = IntegerProperty.create("honey_pond_state", 0, 2);
    public static Random rand = new Random();

    public static Properties getProperties()
    {
        return Properties
                .of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.STONE)
                .strength(2f, 30f)
                .noOcclusion();
    }

    public HoneyPondBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(HONEY_POND_STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(HONEY_POND_STATE);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        super.use(blockstate, level, pos, player, hand, hit);

        if (new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.BLOCK_REACH.get()) / 2)
            return InteractionResult.FAIL;

        int healingCapacity = 0;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof HoneyPondBlockEntity)
            healingCapacity = ((HoneyPondBlockEntity) blockEntity).getHealingCapacity();

        if (player.getMainHandItem().is(Items.HONEY_BOTTLE)) {
            if (healingCapacity == Config.Baked.honeyPondMaxCapacity) {
                player.displayClientMessage(Component.translatable("block." + LegendaryAdditions.MOD_ID + ".honey_pond.already_full"), true);
            } else {
                player.getMainHandItem().shrink(1);

                if (blockEntity instanceof HoneyPondBlockEntity)
                    ((HoneyPondBlockEntity) blockEntity).addHealingCharges(Config.Baked.honeyPondHoneyCapacityRestored);
                if (level.isClientSide) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                }
            }
        } else {
            if (healingCapacity > 0) {
                if (player.getHealth() == player.getMaxHealth() && !LegendarySurvivalOverhaulUtil.isALimbDamaged(player)) {
                    player.displayClientMessage(Component.translatable("block." + LegendaryAdditions.MOD_ID + ".honey_pond.max_health"), true);
                } else {
                    player.heal(Config.Baked.honeyPondHealthRestored);
                    LegendarySurvivalOverhaulUtil.healMostDamagedLimb(player, Config.Baked.honeyPondLimbHealthRestored);
                    if (level.isClientSide) {
                        for (int i = 0; i < Math.round((float) Config.Baked.honeyPondHealthRestored / 2.0f); i++) {
                            float xr = rand.nextFloat() / 2 + 0.25f;
                            float yr = rand.nextFloat() / 2 + 0.75f;
                            float zr = rand.nextFloat() / 2 + 0.25f;
                            level.addParticle(ParticleTypes.HEART, false, pos.getX() + xr, pos.getY() + yr, pos.getZ() + zr, 0, 0, 0);
                        }
                        level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                    }

                    ((HoneyPondBlockEntity) blockEntity).useOneHealingCharge();
                }
            } else {
                player.displayClientMessage(Component.translatable("block." + LegendaryAdditions.MOD_ID + ".honey_pond.empty"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof HoneyPondBlockEntity) {
                int healingCapacity = ((HoneyPondBlockEntity) blockEntity).getHealingCapacity();

                int nbHoneyBottle = (int) ((float) healingCapacity / (float) Config.Baked.honeyPondHoneyCapacityRestored);
                ItemStack honeyBottles = new ItemStack(Items.HONEY_BOTTLE);
                honeyBottles.setCount(nbHoneyBottle);
                popResource(level, pos, honeyBottles);
            }
            if (blockEntity != null)
                blockEntity.setRemoved();
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.HONEY_POND_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
