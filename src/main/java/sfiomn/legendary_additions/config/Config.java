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

		public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> obeliskXpValues;
		public final ForgeConfigSpec.BooleanValue obeliskBreakable;

		public final ForgeConfigSpec.ConfigValue<List<? extends String>> cloverPatchBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> cloverPatchBiomeCategories;
		public final ForgeConfigSpec.IntValue cloverPatchCount;
		public final ForgeConfigSpec.IntValue cloverPatchTries;

		public final ForgeConfigSpec.ConfigValue<List<? extends String>> glowingBulbBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> glowingBulbBiomeCategories;
		public final ForgeConfigSpec.IntValue glowingBulbSpawnChance;

		public final ForgeConfigSpec.BooleanValue forestDungeonGateCanClose;
		public final ForgeConfigSpec.BooleanValue forestDungeonGateOpenWhenUnlocked;
		public final ForgeConfigSpec.BooleanValue forestDungeonGateBreakable;
		public final ForgeConfigSpec.BooleanValue forestDungeonGateDrop;
		public final ForgeConfigSpec.BooleanValue forestDungeonGateDropKeys;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> forestDungeonGateLock1Unlocks;
		public final ForgeConfigSpec.IntValue forestDungeonGateMobCheckRange;
		public final ForgeConfigSpec.IntValue forestDungeonGateMobCheckFrequency;

		public final ForgeConfigSpec.ConfigValue<List<? extends String>> dungeonHeartItemsBlocked;
		public final ForgeConfigSpec.BooleanValue dungeonHeartBlockPlaceBlocked;
		public final ForgeConfigSpec.BooleanValue dungeonHeartBlockBreakBlocked;
		public final ForgeConfigSpec.BooleanValue forestDungeonHeartActiveBreakable;
		public final ForgeConfigSpec.BooleanValue forestDungeonHeartBreakable;
		public final ForgeConfigSpec.BooleanValue forestDungeonHeartDrop;
		public final ForgeConfigSpec.ConfigValue<? extends String> forestDungeonHeartDeactivationByItem;
		public final ForgeConfigSpec.BooleanValue forestDungeonHeartDeactivationByRedStone;

		public final ForgeConfigSpec.IntValue spiderEggsHorizontalDetectionRangeInBlocks;
		public final ForgeConfigSpec.IntValue spiderEggsYDetectionRangeInBlocks;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> spiderEggsMobsSpawned;

		Common(ForgeConfigSpec.Builder builder)
		{
			builder.comment(new String [] {
						" Options related to the Meat Rack. It is used to decay rotten meat to leather and then to bone"
					}).push("MeatRack");

			meatRackLeatherTicks = builder.defineInRange("Tick Amount Before Leather", 240, 20, 100000);
			meatRackBoneTicks = builder.defineInRange("Tick Amount Before Bone", 240, 20, 100000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Honey Pond. It is used to heal the player and can be recharger with honey bottle"
			}).push("HoneyPond");

			honeyPondMaxCapacity = builder.defineInRange("Max Healing Charges", 10, 1, 100000);
			honeyPondHealthRestored = builder.defineInRange("Health Restored Per Use", 6, 1, 100000);
			honeyPondHoneyCapacityRestored = builder.defineInRange("Healing Charges Restored By Honey Bottle", 5, 0, 100000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Xp Storage. It stores and gives back player experience"
			}).push("XpStorage");
			xpStorageMaxXpCapacity = builder.defineInRange("Maximum Stored Experience", 2000, 0, 10000000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to explosion when breaking Coal Ore blocks with Iron Pickaxe."
			}).push("IronOnCoalExplosion");
			ironOnCoalExplosionEnabled = builder.define("Enable Iron Pickaxe On Coal Block Explosion", true);
			ironOnCoalExplosionChance = builder.defineInRange("Chance Of Explosion", 0.03, 0.0, 1.0);
			builder.pop();

			builder.push("Obelisk");
			obeliskXpValues = builder.define(" Default Obelisk Xp Values In Creative Tab", Arrays.asList(250, 500, 1000, 1500));
			obeliskBreakable = builder.define(" Can Obelisk Be Destroyed", false);
			builder.pop();

			builder.push("Flowers");
			builder.push("CloverPatch");
			cloverPatchBiomeNames = builder.comment(" In Which Biome Names The Clover Patch Will Spawn").define("Clover Patch Biome Names Spawn List", new ArrayList<>());
			cloverPatchBiomeCategories = builder.comment(" In Which Biome Categories The Clover Patch Will Spawn").define("Clover Patch Biome Categories Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			cloverPatchCount = builder.comment(" Number of clover patches that spawn in the same time").defineInRange("Clover Patch Count", 4, 0, Integer.MAX_VALUE);
			cloverPatchTries = builder.comment(" Number of tries to spawn a group of clover patch").defineInRange("Clover Patch Tries", 4, 0, Integer.MAX_VALUE);
			builder.pop();
			builder.push("GlowingBulb");
			glowingBulbBiomeNames = builder.comment(" In Which Biome Names The Glowing Bulb Will Spawn").define("Glowing Bulb Biome Names Spawn List", new ArrayList<>());
			glowingBulbBiomeCategories = builder.comment(" In Which Biome Categories The Glowing Bulb Will Spawn").define("Glowing Bulb Biome Categories Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			glowingBulbSpawnChance = builder.comment(" 1/X chance to spawn a glowing bulb patch per chunk. Increasing this number reduces the spawn chance.").defineInRange("Glowing Bulb Chance Spawn", 50, 1, Integer.MAX_VALUE);
			builder.pop();
			builder.pop();

			builder.push("DungeonGates");
			builder.push("ForestDungeonGate");
			forestDungeonGateCanClose = builder.define(" Can Forest Dungeon Gate Be Closed Back", true);
			forestDungeonGateOpenWhenUnlocked = builder.define(" Will Forest Dungeon Gate Open When Unlocked", false);
			forestDungeonGateBreakable = builder.define(" Can Forest Dungeon Gate Be Destroyed", true);
			forestDungeonGateDrop = builder.define(" Can Forest Dungeon Gate Be Dropped", true);
			forestDungeonGateDropKeys = builder.define(" Can Forest Dungeon Gate Drop Keys On Break", true);
			forestDungeonGateLock1Unlocks = builder.define(" Items To Unlock Lock1", Collections.singletonList(LegendaryAdditions.MOD_ID + ":forest_key"));
			forestDungeonGateMobCheckRange = builder.comment(" To choose a mob for which its presence forces the gate to stay locked, " +
					"use an entity spawn egg and use it on the gate in creative mode.").defineInRange(" Mob Check Range In Blocks", 20, 0, 1000);
			forestDungeonGateMobCheckFrequency = builder.defineInRange(" Mob Check Frequency In Ticks", 20, 1, 10000);
			builder.pop();
			builder.push("DesertDungeonGate");
			builder.pop();
			builder.push("IcyDungeonGate");
			builder.pop();
			builder.push("OceanDungeonGate");
			builder.pop();
			builder.push("DesertDungeonGate");
			builder.pop();
			builder.push("DesertDungeonGate");
			builder.pop();
			builder.pop();

			builder.push("DungeonHearts");
			builder.push("Effects");
			// grapplemod:grapplinghook
			dungeonHeartItemsBlocked = builder.define(" Items Disabled While Dungeon Heart Active", Collections.singletonList("minecraft:stick"));
			dungeonHeartBlockPlaceBlocked = builder.define(" Block Placement Disabled While Dungeon Heart Active", true);
			dungeonHeartBlockBreakBlocked = builder.define(" Block Break Disabled While Dungeon Heart Active", true);
			builder.pop();
			builder.push("ForestDungeonHeartBlock");
			forestDungeonHeartActiveBreakable = builder.comment("If Forest Dungeon Heart can't be destroyed, this configuration is de facto set to false").define(" Can Forest Dungeon Heart Be Destroyed While Active", true);
			forestDungeonHeartBreakable = builder.define(" Can Forest Dungeon Heart Be Destroyed", true);
			forestDungeonHeartDrop = builder.define(" Can Forest Dungeon Heart Be Dropped", true);
			forestDungeonHeartDeactivationByItem = builder.comment("No item defined means no item needed to deactivate it").define(" Item Use To Deactivate Forest Dungeon Heart", "minecraft:apple");
			forestDungeonHeartDeactivationByRedStone = builder.define(" Can Forest Dungeon Heart Be Deactivated By Receiving RedStone Signal", true);
			builder.pop();
			builder.pop();

			builder.push("Spawners");
			builder.push("SpiderEggs");
			spiderEggsHorizontalDetectionRangeInBlocks = builder.defineInRange(" Horizontal Detection Range Of Spider Eggs In Blocks", 10, 1, 1000);
			spiderEggsYDetectionRangeInBlocks = builder.defineInRange(" Y Ratio Detection Range Of Spider Eggs", 4, 1, 1000);
			spiderEggsMobsSpawned = builder.define(" Detection Range Of Spider Eggs In Blocks", Collections.singletonList("minecraft:spider;20"));
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

		public static List<? extends Integer> obeliskXpValues;
		public static boolean obeliskBreakable;

		public static List<? extends String> cloverPatchBiomeNames;
		public static List<? extends String> cloverPatchBiomeCategories;
		public static int cloverPatchCount;
		public static int cloverPatchTries;

		public static List<? extends String> glowingBulbBiomeNames;
		public static List<? extends String> glowingBulbBiomeCategories;
		public static int glowingBulbSpawnChance;

		public static boolean forestDungeonGateCanClose;
		public static boolean forestDungeonGateOpenWhenUnlocked;
		public static boolean forestDungeonGateBreakable;
		public static boolean forestDungeonGateDrop;
		public static boolean forestDungeonGateDropKeys;
		public static List<? extends String> forestDungeonGateLock1Unlocks;
		public static int forestDungeonGateMobCheckRange;
		public static int forestDungeonGateMobCheckFrequency;

		public static List<? extends String> dungeonHeartItemsBlocked;
		public static boolean dungeonHeartBlockPlaceBlocked;
		public static boolean dungeonHeartBlockBreakBlocked;
		public static boolean forestDungeonHeartActiveBreakable;
		public static boolean forestDungeonHeartBreakable;
		public static boolean forestDungeonHeartDrop;
		public static String forestDungeonHeartDeactivationByItem;
		public static boolean forestDungeonHeartDeactivationByRedStone;

		public static int spiderEggsHorizontalDetectionRangeInBlocks;
		public static float spiderEggsYDetectionRangeInBlocks;
		public static List<? extends String> spiderEggsMobsSpawned;

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

				cloverPatchBiomeNames = COMMON.cloverPatchBiomeNames.get();
				cloverPatchBiomeCategories = COMMON.cloverPatchBiomeCategories.get();
				cloverPatchCount = COMMON.cloverPatchCount.get();
				cloverPatchTries = COMMON.cloverPatchTries.get();

				glowingBulbBiomeNames = COMMON.glowingBulbBiomeNames.get();
				glowingBulbBiomeCategories = COMMON.glowingBulbBiomeCategories.get();
				glowingBulbSpawnChance = COMMON.glowingBulbSpawnChance.get();

				forestDungeonGateCanClose = COMMON.forestDungeonGateCanClose.get();
				forestDungeonGateOpenWhenUnlocked = COMMON.forestDungeonGateOpenWhenUnlocked.get();
				forestDungeonGateBreakable = COMMON.forestDungeonGateBreakable.get();
				forestDungeonGateDrop = COMMON.forestDungeonGateDrop.get();
				forestDungeonGateDropKeys = COMMON.forestDungeonGateDropKeys.get();
				forestDungeonGateLock1Unlocks = COMMON.forestDungeonGateLock1Unlocks.get();
				forestDungeonGateMobCheckRange = COMMON.forestDungeonGateMobCheckRange.get();
				forestDungeonGateMobCheckFrequency = COMMON.forestDungeonGateMobCheckFrequency.get();

				dungeonHeartItemsBlocked = COMMON.dungeonHeartItemsBlocked.get();
				dungeonHeartBlockPlaceBlocked = COMMON.dungeonHeartBlockPlaceBlocked.get();
				dungeonHeartBlockBreakBlocked = COMMON.dungeonHeartBlockBreakBlocked.get();

				forestDungeonHeartActiveBreakable = COMMON.forestDungeonHeartActiveBreakable.get();
				forestDungeonHeartBreakable = COMMON.forestDungeonHeartBreakable.get();
				forestDungeonHeartDrop = COMMON.forestDungeonHeartDrop.get();
				forestDungeonHeartDeactivationByItem = COMMON.forestDungeonHeartDeactivationByItem.get();
				forestDungeonHeartDeactivationByRedStone = COMMON.forestDungeonHeartDeactivationByRedStone.get();

				spiderEggsHorizontalDetectionRangeInBlocks = COMMON.spiderEggsHorizontalDetectionRangeInBlocks.get();
				spiderEggsYDetectionRangeInBlocks = COMMON.spiderEggsYDetectionRangeInBlocks.get();
				spiderEggsMobsSpawned = COMMON.spiderEggsMobsSpawned.get();
			}
			catch (Exception e)
			{
				LegendaryAdditions.LOGGER.warn("An exception was caused trying to load the common config for Survival Overhaul");
				e.printStackTrace();
			}
		}
	}
}
