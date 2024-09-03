package sfiomn.legendary_additions.blocks;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeMod;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.blockentities.ObeliskBlockEntity;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.BlockEntityRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

public class ObeliskBlock extends BaseEntityBlock {
    private static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_BASE = Block.box(4.0D, 16.0D, 4.0D, 12.0D, 31.0D, 12.0D);
    private static final VoxelShape TOP_DOWN_BASE = Block.box(4.0D, 16.0D, 4.0D, 12.0D, 27.0D, 12.0D);
    private static final VoxelShape TOP = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    private static final VoxelShape BASE_TOP = Block.box(0.0D, -16.0D, 0.0D, 16.0D, 0D, 16.0D);
    private static final VoxelShape TOP_DOWN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);
    private static final VoxelShape SHAPE_BASE = Shapes.or(BASE, TOP_BASE);
    private static final VoxelShape SHAPE_BASE_DOWN = Shapes.or(BASE, TOP_DOWN_BASE);
    private static final VoxelShape SHAPE_TOP = Shapes.or(BASE_TOP, TOP);
    private static final VoxelShape SHAPE_TOP_DOWN = Shapes.or(BASE_TOP, TOP_DOWN);
    public static final BooleanProperty OBELISK_DOWN = BooleanProperty.create("obelisk_down");
    public static final EnumProperty<ObeliskPart> PART = EnumProperty.create("part", ObeliskPart.class);

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        Properties properties = Properties
                .of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.STONE)
                .strength(12f, 100f)
                .lightLevel((state) -> state.getValue(OBELISK_DOWN) ? 0: 15)
                .noOcclusion();

        if (!Config.Baked.obeliskBreakable)
            properties.strength(50f, 1200f);

        return properties;
    }

    public ObeliskBlock() {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(OBELISK_DOWN, false).setValue(PART, ObeliskPart.BASE));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (state.hasProperty(PART)) {
            if (state.getValue(PART) == ObeliskPart.BASE) {
                return RenderShape.MODEL;
            }
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(PART).isBase() ? state.getValue(OBELISK_DOWN) ? SHAPE_BASE_DOWN : SHAPE_BASE : state.getValue(OBELISK_DOWN) ? SHAPE_TOP_DOWN : SHAPE_TOP;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(OBELISK_DOWN, PART);
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

        BlockPos basePos = getBasePos(blockstate, pos);

        BlockEntity blockEntity = level.getBlockEntity(basePos);
        if (blockEntity instanceof ObeliskBlockEntity obeliskBlockEntity) {
            int remainingXp = obeliskBlockEntity.getXp();

            int xpGiven = Math.min(obeliskBlockEntity.getXpCapacity() / 10, remainingXp);
            remainingXp -= xpGiven;

            if (xpGiven > 0) {
                if (level instanceof ServerLevel serverLevel) {
                    if (isBase(blockstate))
                        popExperience(serverLevel, pos.offset(hit.getDirection().getNormal()), xpGiven);
                    else {
                        if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !serverLevel.restoringBlockSnapshots) {
                            ExperienceOrb.award(serverLevel, Vec3.atCenterOf(pos)
                                    .add(hit.getDirection().getNormal().getX() * 0.5,
                                         0,
                                         hit.getDirection().getNormal().getZ() * 0.5), xpGiven);
                        }
                    }
                }

                if (level instanceof ClientLevel) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0f, 1.0f, false);
                }
            }

            if (xpGiven > 0) {
                obeliskBlockEntity.setXp(remainingXp);

                if (remainingXp == 0 ) {
                    obeliskBlockEntity.setDown();
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!canPlace(context))
            return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, player, itemStack);
        world.setBlockAndUpdate(pos.above(), state.setValue(PART, ObeliskPart.TOP));
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if (!blockState.is(newBlockState.getBlock())) {
            if (blockState.getValue(PART).isBase()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof ObeliskBlockEntity && level instanceof ServerLevel) {
                    popExperience((ServerLevel) level, pos, ((ObeliskBlockEntity) blockEntity).getXp());
                }
                if (level.getBlockState(pos.above()).is(this))
                    level.removeBlock(pos.above(), false);
            } else {
                if (level.getBlockState(pos.below()).is(this))
                    level.removeBlock(pos.below(), false);
            }
        }
        super.onRemove(blockState, level, pos, newBlockState, isMoving);
    }

    public boolean isBase(BlockState state) {
        return state.getValue(PART).isBase();
    }
    public BlockPos getBasePos(BlockState state, BlockPos pos) {
        return isBase(state) ? pos : pos.below();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(PART).isBase())
            return BlockEntityRegistry.OBELISK_BLOCK_ENTITY.get().create(blockPos, blockState);
        return null;
    }

    private boolean canPlace(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (pos.getY() > level.getHeight() - 2) {
            return false;
        }

        if (level.getBlockState(pos.above()).isSolidRender(level, pos.above())) {
            return false;
        }

        return true;
    }

    public enum ObeliskPart implements StringRepresentable {
        BASE("base"),
        TOP("top");

        private String name;

        ObeliskPart(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public boolean isBase() {
            return Objects.equals(this.name, "base");
        }
    }
}
