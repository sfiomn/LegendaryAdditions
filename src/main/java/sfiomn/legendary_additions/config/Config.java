package sfiomn.legendary_additions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import sfiomn.legendary_additions.LegendaryAdditions;

import java.util.*;

public class Config
{
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	static
	{
		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}
	
	public static void register()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC, LegendaryAdditions.MOD_ID + ".toml");
	}
	
	public static class Common
	{
		public final ForgeConfigSpec.IntValue meatRackLeatherTicks;
		public final ForgeConfigSpec.IntValue meatRackBoneTicks;

		public final ForgeConfigSpec.IntValue honeyPondMaxCapacity;
		public final ForgeConfigSpec.IntValue honeyPondHealthRestored;
		public final ForgeConfigSpec.IntValue honeyPondHoneyCapacityRestored;

		public final ForgeConfigSpec.IntValue xpStorageMaxXpCapacity;

		public final ForgeConfigSpec.BooleanValue ironOnCoalExplosionEnabled;
		public final ForgeConfigSpec.DoubleValue ironOnCoalExplosionChance;

		public final ForgeConfigSpec.ConfigValue<List<Integer>> obeliskXpValues;
		public final ForgeConfigSpec.BooleanValue obeliskBreakable;

		public final ForgeConfigSpec.IntValue spiderEggsHorizontalDetectionRangeInBlocks;
		public final ForgeConfigSpec.IntValue spiderEggsYDetectionRangeInBlocks;
		public final ForgeConfigSpec.ConfigValue<List<String>> spiderEggsMobsSpawned;

		public final ForgeConfigSpec.IntValue mudTrapHeightPoisonGas;
		public final ForgeConfigSpec.IntValue mudTrapDiameterPoisonGas;

		Common(ForgeConfigSpec.Builder builder)
		{
			builder.comment(" Options related to the Meat Rack. It is used to decay rotten meat to leather and then to bone").push("MeatRack");

			meatRackLeatherTicks = builder.defineInRange("Tick Amount Before Leather, 20 ticks = 1s", 2400, 1, 100000);
			meatRackBoneTicks = builder.defineInRange("Tick Amount Before Bone, 20 ticks = 1s", 2400, 1, 100000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Honey Pond. It is used to heal the player and can be recharger with honey bottle"
			}).push("HoneyPond");

			honeyPondMaxCapacity = builder.defineInRange("Max Healing Charges", 10, 0, 100000);
			honeyPondHealthRestored = builder.defineInRange("Health Restored Per Use", 6, 0, 100000);
			honeyPondHoneyCapacityRestored = builder.defineInRange("Healing Charges Restored By Honey Bottle", 5, 0, 100000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Xp Storage. It stores and gives back player experience"
			}).push("XpStorage");
			xpStorageMaxXpCapacity = builder.defineInRange("Maximum Stored Experience", 2000, 0, 1000000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to explosion when breaking Coal Ore blocks with Iron Pickaxe."
			}).push("IronOnCoalExplosion");
			ironOnCoalExplosionEnabled = builder.define("Enable Iron Pickaxe On Coal Block Explosion", true);
			ironOnCoalExplosionChance = builder.defineInRange("Chance Of Explosion", 0.03, 0, 1);
			builder.pop();

			builder.push("Obelisk");
			obeliskXpValues = builder.define(" Default Obelisk Xp Values In Creative Tab", Arrays.asList(250, 500, 1000, 1500));
			obeliskBreakable = builder.define(" Can Obelisk Be Destroyed", false);
			builder.pop();

			builder.push("MudTrap");
			mudTrapHeightPoisonGas = builder.defineInRange(" Default Height Of The Mud Trap Poison Gas", 3, 0, 100);
			mudTrapDiameterPoisonGas = builder.defineInRange(" Default Diameter Of The Mud Trap Poison Gas", 1, 0 , 100);
			builder.pop();

			builder.push("Spawners");
			builder.push("SpiderEggs");
			spiderEggsHorizontalDetectionRangeInBlocks = builder.defineInRange(" Horizontal Detection Range Of Spider Eggs In Blocks", 10, 1, 1000);
			spiderEggsYDetectionRangeInBlocks = builder.defineInRange(" Y Detection Range Of Spider Eggs", 4, 1, 1000);
			spiderEggsMobsSpawned = builder.define(" Mobs spawning when activated. Formatted like 'mob namespace; weight'", Collections.singletonList("minecraft:spider;20"));
			builder.pop();
			builder.pop();
		}
	}
	
	public static class Baked
	{
		public static int meatRackLeatherTicks;
		public static int meatRackBoneTicks;

		public static int honeyPondMaxCapacity;
		public static int honeyPondHealthRestored;
		public static int honeyPondHoneyCapacityRestored;

		public static int xpStorageMaxXpCapacity;

		public static boolean ironOnCoalExplosionEnabled;
		public static double ironOnCoalExplosionChance;

		public static List<Integer> obeliskXpValues;
		public static boolean obeliskBreakable;

		public static int spiderEggsHorizontalDetectionRangeInBlocks;
		public static float spiderEggsYDetectionRangeInBlocks;
		public static List<String> spiderEggsMobsSpawned;

		public static int mudTrapHeightPoisonGas;
		public static int mudTrapDiameterPoisonGas;

		public static void bakeCommon()
		{
			try
			{
				meatRackLeatherTicks = COMMON.meatRackLeatherTicks.get();
				meatRackBoneTicks = COMMON.meatRackBoneTicks.get();

				honeyPondMaxCapacity = COMMON.honeyPondMaxCapacity.get();
				honeyPondHealthRestored = COMMON.honeyPondHealthRestored.get();
				honeyPondHoneyCapacityRestored = COMMON.honeyPondHoneyCapacityRestored.get();

				xpStorageMaxXpCapacity = COMMON.xpStorageMaxXpCapacity.get();

				ironOnCoalExplosionEnabled = COMMON.ironOnCoalExplosionEnabled.get();
				ironOnCoalExplosionChance = COMMON.ironOnCoalExplosionChance.get();

				obeliskXpValues = COMMON.obeliskXpValues.get();
				obeliskBreakable = COMMON.obeliskBreakable.get();

				spiderEggsHorizontalDetectionRangeInBlocks = COMMON.spiderEggsHorizontalDetectionRangeInBlocks.get();
				spiderEggsYDetectionRangeInBlocks = COMMON.spiderEggsYDetectionRangeInBlocks.get();
				spiderEggsMobsSpawned = COMMON.spiderEggsMobsSpawned.get();

				mudTrapHeightPoisonGas = COMMON.mudTrapHeightPoisonGas.get();
				mudTrapDiameterPoisonGas = COMMON.mudTrapDiameterPoisonGas.get();
			}
			catch (Exception e)
			{
				LegendaryAdditions.LOGGER.warn("An exception was caused trying to load the common config for Survival Overhaul");
				e.printStackTrace();
			}
		}
	}
}
