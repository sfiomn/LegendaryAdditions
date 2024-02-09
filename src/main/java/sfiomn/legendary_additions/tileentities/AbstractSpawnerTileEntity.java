package sfiomn.legendary_additions.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;
import sfiomn.legendary_additions.registry.EffectRegistry;

import javax.annotation.Nullable;
import java.util.Map;

abstract class AbstractSpawnerTileEntity extends TileEntity implements ITickableTileEntity {

    private int updateCounter;
    private int spawnNumber;

    public AbstractSpawnerTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void tick() {
        if (this.level == null)
            return;

        if(!this.level.isClientSide){
            if(this.updateCounter++ > 60 && getAvailableSpawnNumber() > 0){
                int XZRange = getHorizontalDetectionRangeInBlocks();
                int YRange = getYDetectionRangeInBlocks();

                for (PlayerEntity player: this.level.players()) {
                    double playerPositionInRange = getPlayerPositionInRange(player, XZRange, YRange);
                    if (playerPositionInRange <= 1) {
                        String mobRegistry = pickMobRegistry();
                        int quantity = 1;
                        if (playerPositionInRange <= 0.5) {
                            quantity = Math.min(2, getAvailableSpawnNumber());
                        }

                        spawnMobs(mobRegistry, quantity);
                        spawnNumber += quantity;
                    }
                }
            }
        }
    }

    // Return the position of the player within the spheroid along the range. <= 1 means within the spheroid
    private double getPlayerPositionInRange(PlayerEntity player, int XZRange, float YRange) {
        Vector3d distance = player.position().subtract(Vector3d.atLowerCornerOf(this.worldPosition));

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
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(mobRegistry));
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
            if (entity instanceof MobEntity && ((MobEntity) entity).isPersistenceRequired())
                ((MobEntity) entity).setPersistenceRequired();

            this.level.playSound(null, this.worldPosition, SoundEvents.SILVERFISH_STEP, SoundCategory.HOSTILE, 10.0F, 1.0F);

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
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.setSpawnNumber(nbt.getInt("spawnNumber"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("spawnNumber", this.getSpawnNumber());
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = this.getUpdateTag();
        return new SUpdateTileEntityPacket(this.worldPosition, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.putInt("spawnNumber", this.getSpawnNumber());
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        handleUpdateTag(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        this.setSpawnNumber(nbt.getInt("spawnNumber"));
    }
}
