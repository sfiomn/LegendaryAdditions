package sfiomn.legendary_additions.blocks;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;
import sfiomn.legendary_additions.blockentities.XpStorageBlockEntity;

import javax.annotation.Nullable;

public class XpStorageBlock extends BaseEntityBlock {
    public static final Properties properties = getProperties();;
    public static final IntegerProperty STATE = IntegerProperty.create("xp_storage_state", 0, 3);

    public static Properties getProperties()
    {
        return Properties
                .of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.STONE)
                .strength(20f, 600f)
                .noLootTable()
                .noOcclusion();
    }

    public XpStorageBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(STATE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }


    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        super.use(blockstate, level, pos, player, hand, hit);

        if (new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.BLOCK_REACH.get()))
            return InteractionResult.FAIL;

        if (player.experienceLevel == 0)
            return InteractionResult.PASS;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof XpStorageBlockEntity xpStorageBlockEntity) {

            int maxXpCapacity = xpStorageBlockEntity.getXpCapacity();
            if (maxXpCapacity == 0) {
                xpStorageBlockEntity.setXpCapacity(Config.Baked.xpStorageMaxXpCapacity);
                maxXpCapacity = Config.Baked.xpStorageMaxXpCapacity;
            }

            if (xpStorageBlockEntity.getXp() >= maxXpCapacity)
                return InteractionResult.PASS;

            if (level instanceof ClientLevel) {
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
            }

            int newPlayerLevel = player.experienceLevel - 1;

            int xpLevel = this.getXpNeededForNextLevel(newPlayerLevel);

            int xpStored;
            if (xpLevel > maxXpCapacity)
                xpStored = maxXpCapacity;
            else
                xpStored = Math.min(xpLevel, maxXpCapacity - xpStorageBlockEntity.getXp());

            player.giveExperiencePoints(-xpStored);

            MinecraftForge.EVENT_BUS.post(new PlayerXpEvent.XpChange(player, -xpStored));

            xpStorageBlockEntity.setXp(xpStorageBlockEntity.getXp() + xpStored);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public int getXpNeededForNextLevel(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof XpStorageBlockEntity && level instanceof ServerLevel) {
                popExperience((ServerLevel) level, pos, ((XpStorageBlockEntity) tileEntity).getXp());
            }
            super.onRemove(blockState, level, pos, newBlockState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.XP_STORAGE_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
