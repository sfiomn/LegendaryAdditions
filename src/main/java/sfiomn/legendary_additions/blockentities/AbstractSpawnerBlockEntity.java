package sfiomn.legendary_additions.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

abstract class AbstractSpawnerBlockEntity extends BlockEntity {

    private int updateCounter;
    private int spawnNumber;

    public AbstractSpawnerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractSpawnerBlockEntity entity) {
        if (level == null)
            return;

        if(!level.isClientSide){
            if(entity.updateCounter++ > 60 && entity.getAvailableSpawnNumber() > 0){
                int XZRange = entity.getHorizontalDetectionRangeInBlocks();
                int YRange = entity.getYDetectionRangeInBlocks();

                for (Player player: level.players()) {
                    double playerPositionInRange = entity.getPlayerPositionInRange(player, XZRange, YRange);
                    if (playerPositionInRange <= 1) {
                        String mobRegistry = entity.pickMobRegistry();
                        int quantity = 1;
                        if (playerPositionInRange <= 0.5) {
                            quantity = Math.min(2, entity.getAvailableSpawnNumber());
                        }

                        entity.spawnMobs(mobRegistry, quantity);
                        entity.spawnNumber += quantity;
                    }
                }
            }
        }
    }

    // Return the position of the player within the spheroid along the range. <= 1 means within the spheroid
    private double getPlayerPositionInRange(Player player, int XZRange, float YRange) {
        Vec3 distance = player.position().subtract(Vec3.atLowerCornerOf(this.worldPosition));

        // As XZrange and Yrange might be different, the range calculated is inside a spheroid
        return (Math.pow(distance.x, 2) + Math.pow(distance.z, 2)) / Math.pow(XZRange, 2) +
                (Math.pow(distance.y, 2) / Math.pow(YRange, 2));
    }

    private String pickMobRegistry() {
        if (this.level == null)
            return "";
        int totalWeight = 0;
        for (Map.Entry<String, Integer> mobSpawn: getMobWeightList().entrySet()) {
            totalWeight += mobSpawn.getValue();
        }
        if (totalWeight > 0) {
            int mobPickedWeight = this.level.random.nextInt(totalWeight);

            for (Map.Entry<String, Integer> mobSpawn: getMobWeightList().entrySet()) {
                if (mobPickedWeight <= mobSpawn.getValue())
                    return mobSpawn.getKey();
                else
                    mobPickedWeight -= mobSpawn.getValue();
            }
        }
        return "";
    }

    private void spawnMobs(String mobRegistry, int quantity) {
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(mobRegistry));
        if (entityType == null || this.level == null) {
            return;
        }

        for (int l = 0; l < quantity; ++l) {
            float f1 = ((float) (l % 2) - 0.5F);
            float f2 = ((float) (l / 2) - 0.5F);

            Entity entity = entityType.create(this.level);
            if (entity == null)
                return;

            entity.setInvulnerable(entity.isInvulnerable());
            if (entity instanceof Mob && ((Mob) entity).isPersistenceRequired())
                ((Mob) entity).setPersistenceRequired();

            this.level.playSound(null, this.worldPosition, SoundEvents.SILVERFISH_STEP, SoundSource.HOSTILE, 10.0F, 1.0F);

            entity.moveTo(this.worldPosition.getX() + (double) f1, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + (double) f2, this.level.random.nextFloat() * 360.0F, 0.0F);
            this.level.addFreshEntity(entity);
        }
    }

    private int getAvailableSpawnNumber() {
        return getSpawnLimit() - this.spawnNumber;
    }

    public void setSpawnNumber(int spawnNumber) {
        this.spawnNumber = spawnNumber;
        this.setChanged();
    }

    public int getSpawnNumber() {
        return spawnNumber;
    }

    abstract int getHorizontalDetectionRangeInBlocks();
    abstract int getYDetectionRangeInBlocks();
    abstract Map<String, Integer> getMobWeightList();
    abstract int getSpawnLimit();

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.setSpawnNumber(nbt.getInt("spawnNumber"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("spawnNumber", this.getSpawnNumber());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("spawnNumber", this.getSpawnNumber());
        return nbt;
    }
}
