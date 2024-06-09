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
		public final ForgeConfigSpec.ConfigValue<Integer> meatRackLeatherTicks;
		public final ForgeConfigSpec.ConfigValue<Integer> meatRackBoneTicks;

		public final ForgeConfigSpec.ConfigValue<Integer> honeyPondMaxCapacity;
		public final ForgeConfigSpec.ConfigValue<Integer> honeyPondHealthRestored;
		public final ForgeConfigSpec.ConfigValue<Integer> honeyPondHoneyCapacityRestored;

		public final ForgeConfigSpec.ConfigValue<Integer> xpStorageMaxXpCapacity;

		public final ForgeConfigSpec.ConfigValue<Boolean> ironOnCoalExplosionEnabled;
		public final ForgeConfigSpec.ConfigValue<Float> ironOnCoalExplosionChance;

		public final ForgeConfigSpec.ConfigValue<List<Integer>> obeliskXpValues;
		public final ForgeConfigSpec.ConfigValue<Boolean> obeliskBreakable;

		public final ForgeConfigSpec.ConfigValue<List<String>> cloverPatchBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<String>> cloverPatchBiomeCategories;
		public final ForgeConfigSpec.ConfigValue<Integer> cloverPatchCount;
		public final ForgeConfigSpec.ConfigValue<Integer> cloverPatchTries;

		public final ForgeConfigSpec.ConfigValue<List<String>> glowingBulbBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<String>> glowingBulbBiomeCategories;
		public final ForgeConfigSpec.ConfigValue<Integer> glowingBulbCount;
		public final ForgeConfigSpec.ConfigValue<Integer> glowingBulbTries;

		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateCanClose;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateOpenWhenUnlocked;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateBreakable;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateDrop;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateDropKeys;
		public final ForgeConfigSpec.ConfigValue<List<String>> forestDungeonGateLock1Unlocks;
		public final ForgeConfigSpec.ConfigValue<Integer> forestDungeonGateMobCheckRange;
		public final ForgeConfigSpec.ConfigValue<Integer> forestDungeonGateMobCheckFrequency;

		public final ForgeConfigSpec.ConfigValue<List<String>> dungeonHeartItemsBlocked;
		public final ForgeConfigSpec.ConfigValue<Boolean> dungeonHeartBlockPlaceBlocked;
		public final ForgeConfigSpec.ConfigValue<Boolean> dungeonHeartBlockBreakBlocked;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonHeartActiveBreakable;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonHeartBreakable;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonHeartDrop;
		public final ForgeConfigSpec.ConfigValue<String> forestDungeonHeartDeactivationByItem;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonHeartDeactivationByRedStone;

		public final ForgeConfigSpec.ConfigValue<Integer> spiderEggsHorizontalDetectionRangeInBlocks;
		public final ForgeConfigSpec.ConfigValue<Integer> spiderEggsYDetectionRangeInBlocks;
		public final ForgeConfigSpec.ConfigValue<List<String>> spiderEggsMobsSpawned;

		Common(ForgeConfigSpec.Builder builder)
		{
			builder.comment(new String [] {
						" Options related to the Meat Rack. It is used to decay rotten meat to leather and then to bone"
					}).push("MeatRack");

			meatRackLeatherTicks = builder.define("Tick Amount Before Leather", 240);
			meatRackBoneTicks = builder.define("Tick Amount Before Bone", 240);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Honey Pond. It is used to heal the player and can be recharger with honey bottle"
			}).push("HoneyPond");

			honeyPondMaxCapacity = builder.define("Max Healing Charges", 10);
			honeyPondHealthRestored = builder.define("Health Restored Per Use", 6);
			honeyPondHoneyCapacityRestored = builder.define("Healing Charges Restored By Honey Bottle", 5);
			builder.pop();

			builder.comment(new String [] {
					" Options related to the Xp Storage. It stores and gives back player experience"
			}).push("XpStorage");
			xpStorageMaxXpCapacity = builder.define("Maximum Stored Experience", 2000);
			builder.pop();

			builder.comment(new String [] {
					" Options related to explosion when breaking Coal Ore blocks with Iron Pickaxe."
			}).push("IronOnCoalExplosion");
			ironOnCoalExplosionEnabled = builder.define("Enable Iron Pickaxe On Coal Block Explosion", true);
			ironOnCoalExplosionChance = builder.define("Chance Of Explosion", 0.03f);
			builder.pop();

			builder.push("Obelisk");
			obeliskXpValues = builder.define(" Default Obelisk Xp Values In Creative Tab", Arrays.asList(250, 500, 1000, 1500));
			obeliskBreakable = builder.define(" Can Obelisk Be Destroyed", false);
			builder.pop();

			builder.push("Flowers");
			builder.push("CloverPatch");
			cloverPatchBiomeNames = builder.comment(" In Which Biome Names The Clover Patch Will Spawn").define("Clover Patch Biome Names Spawn List", new ArrayList<>());
			cloverPatchBiomeCategories = builder.comment(" In Which Biome Categories The Clover Patch Will Spawn").define("Clover Patch Biome Categories Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			cloverPatchCount = builder.comment(" Number of clover patches that spawn in the same time").defineInRange("Clover Patch Tries", 4, 0, Integer.MAX_VALUE);
			cloverPatchTries = builder.comment(" Number of tries to spawn a group of clover patch").defineInRange("Clover Patch Count", 4, 0, Integer.MAX_VALUE);
			builder.pop();
			builder.push("GlowingBulb");
			glowingBulbBiomeNames = builder.comment(" In Which Biome Names The Glowing Bulb Will Spawn").define("Glowing Bulb Biome Names Spawn List", new ArrayList<>());
			glowingBulbBiomeCategories = builder.comment(" In Which Biome Categories The Glowing Bulb Will Spawn").define("Glowing Bulb Biome Categories Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			glowingBulbCount = builder.comment(" Number of glowing bulbs that spawn in the same time").defineInRange("Glowing Bulb Tries", 4, 0, Integer.MAX_VALUE);
			glowingBulbTries = builder.comment(" Number of tries to spawn a group of glowing bulbs").defineInRange("Glowing Bulb Count", 4, 0, Integer.MAX_VALUE);
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
					"use an entity spawn egg and use it on the gate in creative mode.").define(" Mob Check Range In Blocks", 20);
			forestDungeonGateMobCheckFrequency = builder.define(" Mob Check Frequency In Ticks", 20);
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
			spiderEggsHorizontalDetectionRangeInBlocks = builder.define(" Horizontal Detection Range Of Spider Eggs In Blocks", 10);
			spiderEggsYDetectionRangeInBlocks = builder.define(" Y Ratio Detection Range Of Spider Eggs", 4);
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
		public static float ironOnCoalExplosionChance;

		public static List<Integer> obeliskXpValues;
		public static boolean obeliskBreakable;

		public static List<String> cloverPatchBiomeNames;
		public static List<String> cloverPatchBiomeCategories;
		public static int cloverPatchCount;
		public static int cloverPatchTries;

		public static List<String> glowingBulbBiomeNames;
		public static List<String> glowingBulbBiomeCategories;
		public static int glowingBulbCount;
		public static int glowingBulbTries;

		public static boolean forestDungeonGateCanClose;
		public static boolean forestDungeonGateOpenWhenUnlocked;
		public static boolean forestDungeonGateBreakable;
		public static boolean forestDungeonGateDrop;
		public static boolean forestDungeonGateDropKeys;
		public static List<String> forestDungeonGateLock1Unlocks;
		public static int forestDungeonGateMobCheckRange;
		public static int forestDungeonGateMobCheckFrequency;

		public static List<String> dungeonHeartItemsBlocked;
		public static boolean dungeonHeartBlockPlaceBlocked;
		public static boolean dungeonHeartBlockBreakBlocked;
		public static boolean forestDungeonHeartActiveBreakable;
		public static boolean forestDungeonHeartBreakable;
		public static boolean forestDungeonHeartDrop;
		public static String forestDungeonHeartDeactivationByItem;
		public static boolean forestDungeonHeartDeactivationByRedStone;

		public static int spiderEggsHorizontalDetectionRangeInBlocks;
		public static float spiderEggsYDetectionRangeInBlocks;
		public static List<String> spiderEggsMobsSpawned;

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
				glowingBulbCount = COMMON.glowingBulbCount.get();
				glowingBulbTries = COMMON.glowingBulbTries.get();

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
