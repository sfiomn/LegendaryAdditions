package sfiomn.legendary_additions.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import sfiomn.legendary_additions.LegendaryAdditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

		public final ForgeConfigSpec.ConfigValue<List<String>> cloverPatchBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<String>> cloverPatchBiomeCategories;

		public final ForgeConfigSpec.ConfigValue<List<String>> glowingBulbBiomeNames;
		public final ForgeConfigSpec.ConfigValue<List<String>> glowingBulbBiomeCategories;

		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateCanClose;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateUnbreakable;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateDrop;
		public final ForgeConfigSpec.ConfigValue<Boolean> forestDungeonGateDropKeys;
		public final ForgeConfigSpec.ConfigValue<List<String>> forestDungeonGateLock1Unlocks;

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
			builder.pop();

			builder.push("Flowers");
			builder.push("CloverPatch");
			cloverPatchBiomeNames = builder.comment(" In which biome names the Clover Patch will spawn").define("Clover Patch Biome Names Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			cloverPatchBiomeCategories = builder.comment(" In which biome categories the Clover Patch will spawn").define("Clover Patch Biome Categories Spawn List", new ArrayList<>());
			builder.pop();
			builder.push("GlowingBulb");
			glowingBulbBiomeNames = builder.comment(" In which biome names the Glowing Bulb will spawn").define("Glowing Bulb Biome Names Spawn List", Arrays.asList("PLAINS", "FOREST", "TAIGA"));
			glowingBulbBiomeCategories = builder.comment(" In which biome categories the Glowing Bulb will spawn").define("Glowing Bulb Biome Categories Spawn List", new ArrayList<>());
			builder.pop();

			builder.push("DungeonGates");
			builder.push("ForestDungeonGate");
			forestDungeonGateCanClose = builder.define(" Can Forest Dungeon Gate Be Closed Back", true);
			forestDungeonGateUnbreakable = builder.define(" Can Forest Dungeon Gate Be Destroyed", false);
			forestDungeonGateDrop = builder.define(" Can Forest Dungeon Gate Be Harvested", true);
			forestDungeonGateDropKeys = builder.define(" Can Forest Dungeon Gate Drop Keys On Break", true);
			forestDungeonGateLock1Unlocks = builder.define(" Items To Unlock Lock1", Collections.singletonList(LegendaryAdditions.MOD_ID + ":forest_key"));
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

		public static List<String> cloverPatchBiomeNames;
		public static List<String> cloverPatchBiomeCategories;

		public static List<String> glowingBulbBiomeNames;
		public static List<String> glowingBulbBiomeCategories;

		public static boolean forestDungeonGateCanClose;
		public static boolean forestDungeonGateUnbreakable;
		public static boolean forestDungeonGateDrop;
		public static boolean forestDungeonGateDropKeys;
		public static List<String> forestDungeonGateLock1Unlocks;

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

				cloverPatchBiomeNames = COMMON.cloverPatchBiomeNames.get();
				cloverPatchBiomeCategories = COMMON.cloverPatchBiomeCategories.get();

				glowingBulbBiomeNames = COMMON.glowingBulbBiomeNames.get();
				glowingBulbBiomeCategories = COMMON.glowingBulbBiomeCategories.get();

				forestDungeonGateCanClose = COMMON.forestDungeonGateCanClose.get();
				forestDungeonGateUnbreakable = COMMON.forestDungeonGateUnbreakable.get();
				forestDungeonGateDrop = COMMON.forestDungeonGateDrop.get();
				forestDungeonGateDropKeys = COMMON.forestDungeonGateDropKeys.get();
				forestDungeonGateLock1Unlocks = COMMON.forestDungeonGateLock1Unlocks.get();
			}
			catch (Exception e)
			{
				LegendaryAdditions.LOGGER.warn("An exception was caused trying to load the common config for Survival Overhaul");
				e.printStackTrace();
			}
		}
	}
}
