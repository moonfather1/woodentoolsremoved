package moonfather.woodentoolsremoved;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class OptionsHolder
{
	public static class Common
	{
		private static final boolean defaultSimpleModeEnabled = false;
		private static final boolean defaultHardModeEnabled = true;
		private static final int defaultStoneToolsDurabilityMultiplier = 30;
		private static final int defaultStickDropChance = 4;
		private static final boolean defaultGuaranteedFlintDrops = true;
		private static final boolean defaultForceHardModeWithTC = false;
		private static final int defaultWoodenToolDamageInMobHands = 2;

		public final ConfigValue<Boolean> SimpleModeEnabled;
		public final ConfigValue<Boolean> HardModeEnabled;
		public final ConfigValue<Integer> StoneToolsDurabilityMultiplier;
		public final ConfigValue<Integer> StickDropChance;
		public final ConfigValue<Boolean> GuaranteedFlintDrops;
		public final ConfigValue<Boolean> ForceHardModeWithTC;
		public final ConfigValue<Integer> WoodenToolDamageInMobHands;

		public Common(ForgeConfigSpec.Builder builder)
		{
			builder.push("Obtaining stone");
			this.HardModeEnabled = builder.comment("This controls how players obtain cobblestone at the very start of the game. Three options: \n- Simple mode just provides flint pickaxe; get a few stone pieces and forget about your problems. \n- Hard mode is for realistic modpacks; you'll spend a short while and plenty of nerves to progress from wood tier to stone tier. \n- Option 3 is nothing. Neither of the two. If the modpack provides another way for starting player to get stone (from rocks on the ground or another mod's flint pickaxe), just disable both the hard mode and the simple mode.").worldRestart()
					.define("Hard mode enabled", defaultHardModeEnabled);
			this.SimpleModeEnabled = builder.comment("This controls how players obtain cobblestone at the very start of the game. Three options: \n- Simple mode just provides flint pickaxe; get a few stone pieces and forget about your problems. \n- Hard mode is for realistic modpacks; you'll spend a short while and plenty of nerves to progress from wood tier to stone tier. \n- Option 3 is nothing. Neither of the two. If the modpack provides another way for starting player to get stone (from rocks on the ground or another mod's flint pickaxe), just disable both the hard mode and the simple mode.").worldRestart()
					.define("Simple mode enabled", defaultSimpleModeEnabled);
			builder.pop();
			builder.push("Gameplay changes");
			this.StoneToolsDurabilityMultiplier = builder.comment("This option reduced durability of stone tools. It is a percentage: 100 means unchanged (132 durability points), 50 is half, 10 is 10% (13). It is intended to motivate players to move to iron as soon as possible.").worldRestart()
					.defineInRange("Stone tools durability multiplier", defaultStoneToolsDurabilityMultiplier, 1, 100);
			this.StickDropChance = builder.comment("This is a chance (in percents) that a stick will drop from leaves block. This works in addition to base game's paltry 2% chance (separately). You can set to 0 to disable; default (4%) isn't too generous.")
					.defineInRange("Additional stick drop chance", defaultStickDropChance, 0, 100);
			this.GuaranteedFlintDrops = builder.comment("Because Minecraft's gravel logic is stupid (dig through it, find nothing, it's okay, try again until you find flint inside), this mod guarantees three flint drops in ten gravel blocks broken - but only the first ten. After the first set, this feature is off. You can turn this off if you have another mod that takes care of gravel (try \"Modest flint overhaul\" by the author of this mod) or if for some insane reason you like digging through the same gravel block over and over.")
					.define("Guaranteed flint drops", defaultGuaranteedFlintDrops);
			this.WoodenToolDamageInMobHands = builder.comment("This is how much extra damage a wooden sword or tool will deal in mob hands. Option is introduced because some mods add wooden tools to zombies. No effect on tools in player's hands. This is not a total value, it is a bonus added to mob damage plus minimal tool damage.")
													 .defineInRange("Wooden tool damage in mob hands", defaultWoodenToolDamageInMobHands, 0, 10);
			builder.pop();
			builder.push("Interoperability");
			this.ForceHardModeWithTC = builder.comment("Tinker's construct offers flint tools and easily circumvents this mod's HARD mode. By default, this mod backs off when TC is present and disables both the HARD mode and the SIMPLE mode. This option forces the mod not to disable the three recipes it disables when TC is present, but - that's it. This option does not trigger any changes to TC behavior. It is up to modpack maker or the player to resolve the incompatibility.")
					.define("Force hard mode even with TC present", defaultForceHardModeWithTC);
			builder.pop();
		}
	}

	///////////////////////////////////////////////////

	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

	public static boolean IsResolvedModeSimple()
	{
		return OptionsHolder.COMMON.SimpleModeEnabled.get() && ! OptionsHolder.COMMON.HardModeEnabled.get();
	}

	public static boolean IsResolvedModeHard()
	{
		return OptionsHolder.COMMON.HardModeEnabled.get();
	}

	static //constructor
	{
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}
