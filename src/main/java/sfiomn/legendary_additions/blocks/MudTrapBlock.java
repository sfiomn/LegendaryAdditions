package sfiomn.legendary_additions.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendary_additions.LegendaryAdditions;
import sfiomn.legendary_additions.config.Config;
import sfiomn.legendary_additions.registry.BlockRegistry;
import sfiomn.legendary_additions.registry.SoundRegistry;

import java.util.ArrayList;
import java.util.List;

public class MudTrapBlock extends Block {

    public static final Properties properties = getProperties();

    public static Properties getProperties()
    {
        return Properties
                .of()
                .mapColor(MapColor.DIRT)
                .sound(SoundType.MOSS)
                .strength(0.5F)
                .noOcclusion();
    }

    public MudTrapBlock() {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        level.scheduleTick(pos, this, getPoisonRefreshTickDelay(level.random));

        if (!isPowered(level, pos)) {
            return;
        }

        level.playSound((Player) null, pos, SoundRegistry.MUD_TRAP_POWERED.get(), SoundSource.BLOCKS, 0.1f, 1.0f);

        List<BlockPos> poisonedPosList = new ArrayList<>();

        for (int i = 1; i < Config.Baked.mudTrapHeightPoisonGas + 1; i++) {
            BlockPos newCentralPoisonedPos = pos.relative(Direction.Axis.Y, i);

            setPoisonGasAt(level, newCentralPoisonedPos, poisonedPosList);

            for (int j = -Config.Baked.mudTrapDiameterPoisonGas; j < Config.Baked.mudTrapDiameterPoisonGas + 1; j++) {
                for (int k = -Config.Baked.mudTrapDiameterPoisonGas; k < Config.Baked.mudTrapDiameterPoisonGas + 1; k++) {
                    BlockPos newPoisonedPos = newCentralPoisonedPos.relative(Direction.Axis.X, j).relative(Direction.Axis.Z, k);
                    if (!level.getBlockState(newCentralPoisonedPos).isViewBlocking(level, newPoisonedPos)) {
                        setPoisonGasAt(level, newPoisonedPos, poisonedPosList);
                        if (!level.getBlockState(newPoisonedPos).isFaceSturdy(level, newPoisonedPos, Direction.UP)) {
                            spreadGasUp(level, newPoisonedPos, Config.Baked.mudTrapHeightPoisonGas + 1 - i, poisonedPosList);
                        }
                    }
                }
            }

            if (!canSpreadUp(level, newCentralPoisonedPos))
                break;
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_53483_) {
        super.onPlace(state, level, pos, newState, p_53483_);
        level.scheduleTick(pos, this, getPoisonRefreshTickDelay(level.random));
    }

    private boolean isPowered(ServerLevel level, BlockPos pos) {
        if (level != null) {
            return level.hasNeighborSignal(pos);
        }
        return false;
    }

    private int getPoisonRefreshTickDelay(RandomSource rand) {
        return 20 + rand.nextInt(10);
    }

    private void spreadGasUp(ServerLevel level, BlockPos startSpreadPos, int upTo, List<BlockPos> poisonedPosList) {
        for (int l = 1; l < upTo; l++) {
            BlockPos newPoisonedPos = startSpreadPos.relative(Direction.Axis.Y, l);
            setPoisonGasAt(level, newPoisonedPos, poisonedPosList);
            if (!canSpreadUp(level, newPoisonedPos)) {
                break;
            }
        }
    }

    private boolean canSpreadUp(ServerLevel level, BlockPos pos) {
        if (!level.isAreaLoaded(pos, 1)) return false;
        return level.getBlockState(pos).isAir() || (
                !level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP) &&
                        !level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)
                );
    }

    private void setPoisonGasAt(ServerLevel level, BlockPos pos, List<BlockPos> poisonedPosList) {
        if (!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (level.getBlockState(pos).isAir()) {
            if (!poisonedPosList.contains(pos)) {
                level.setBlockAndUpdate(pos, BlockRegistry.POISON_GAS_BLOCK.get().defaultBlockState());
                poisonedPosList.add(pos);
            }
        }
    }
}
