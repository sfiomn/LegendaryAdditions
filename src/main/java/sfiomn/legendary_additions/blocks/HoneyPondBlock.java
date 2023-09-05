package sfiomn.legendary_additions.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.entities.HoneyPondTileEntity;
import sfiomn.legendary_additions.registry.TileEntityRegistry;

import javax.annotation.Nullable;
import java.util.Random;

import static sfiomn.legendary_additions.entities.HoneyPondTileEntity.MAX_HEALING_CAPACITY;

public class HoneyPondBlock extends Block {

    public static final int HEALTH_RESTORED = Config.Baked.honeyPondHealthRestored;
    public static final int HEALING_CAPACITY_RESTORED_HONEY = Config.Baked.honeyPondHoneyCapacityRestored;
    public static final Properties properties = getProperties();
    public static final IntegerProperty HONEY_POND_STATE = IntegerProperty.create("honey_pond_state", 0, 2);
    public static Random rand = new Random();

    public static Properties getProperties()
    {
        return Properties
                .of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2f, 30f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .noOcclusion();
    }
    public HoneyPondBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(HONEY_POND_STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HONEY_POND_STATE);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand,
                                             BlockRayTraceResult hit) {
        super.use(blockstate, world, pos, player, hand, hit);

        if (new Vector3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(player.position()) > player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) / 2)
            return ActionResultType.FAIL;

        int healingCapacity = 0;
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof HoneyPondTileEntity)
            healingCapacity = ((HoneyPondTileEntity) tileEntity).getHealingCapacity();

        if (player.getMainHandItem().sameItem(new ItemStack(Items.HONEY_BOTTLE))) {
            if (healingCapacity == MAX_HEALING_CAPACITY) {
                player.displayClientMessage(new TranslationTextComponent("block." + LegendaryAdditions.MOD_ID + ".honey_pond.already_full"), true);
            } else {
                player.getMainHandItem().shrink(1);

                if (tileEntity instanceof HoneyPondTileEntity)
                    ((HoneyPondTileEntity) tileEntity).addHealingCharges(HEALING_CAPACITY_RESTORED_HONEY);
                if (world instanceof ClientWorld) {
                    world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                }
            }
        } else {
            if (healingCapacity > 0) {
                if (player.getHealth() == player.getMaxHealth()) {
                    player.displayClientMessage(new TranslationTextComponent("block." + LegendaryAdditions.MOD_ID + ".honey_pond.max_health"), true);
                } else {
                    player.heal(HEALTH_RESTORED);
                    if (world instanceof ClientWorld) {
                        for (int i = 0; i < Math.round((float) HEALTH_RESTORED / 2.0f); i++) {
                            float xr = rand.nextFloat() / 2 + 0.25f;
                            float yr = rand.nextFloat() / 2 + 0.75f;
                            float zr = rand.nextFloat() / 2 + 0.25f;
                            world.addParticle(ParticleTypes.HEART, false, pos.getX() + xr, pos.getY() + yr, pos.getZ() + zr, 0, 0, 0);
                        }
                        world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0f, 1.0f, false);
                    }

                    ((HoneyPondTileEntity) tileEntity).useOneHealingCharge();
                }
            } else {
                player.displayClientMessage(new TranslationTextComponent("block." + LegendaryAdditions.MOD_ID + ".honey_pond.empty"), true);
            }
        }
        return ActionResultType.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof HoneyPondTileEntity) {
                int healingCapacity = ((HoneyPondTileEntity) tileEntity).getHealingCapacity();

                int nbHoneyBottle = (int) ((float) healingCapacity / (float) HEALING_CAPACITY_RESTORED_HONEY);
                ItemStack honeyBottles = new ItemStack(Items.HONEY_BOTTLE);
                honeyBottles.setCount(nbHoneyBottle);
                popResource(world, pos, honeyBottles);
            }
            if (tileEntity != null)
                tileEntity.setRemoved();
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegistry.HONEY_POND_TILE_ENTITY.get().create();
    }
}
