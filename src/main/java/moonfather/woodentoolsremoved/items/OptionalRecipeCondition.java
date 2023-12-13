package moonfather.woodentoolsremoved.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public class OptionalRecipeCondition implements ICondition
{
	private final String flagCode;

	private OptionalRecipeCondition(String value)
	{
		this.flagCode = value;
	}



	@Override
	public boolean test(IContext context)
	{
		if (this.flagCode.equals("allow_pick"))
		{
			return OptionsHolder.IsResolvedModeSimple()
					&& ! ModList.get().isLoaded("tconstruct");
		}
		else if (this.flagCode.equals("allow_powder_bowl"))
		{
			return OptionsHolder.IsResolvedModeHard()
					&& (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get());
		}
		else if (this.flagCode.equals("allow_javelin"))
		{
			return OptionsHolder.IsResolvedModeHard()
					&& (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get());
		}
		else if (this.flagCode.equals("allow_firepit"))
		{
			return OptionsHolder.IsResolvedModeHard()
					&& (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get());
		}
		else if (this.flagCode.equals("hard_mode"))
		{
			return OptionsHolder.IsResolvedModeHard();
		}
		else if (this.flagCode.equals("simple_mode"))
		{
			return OptionsHolder.IsResolvedModeSimple();
		}
		else if (this.flagCode.equals("allow_firestarter"))
		{
			return OptionsHolder.COMMON.EnableFirestarter.get();
		}
		else
		{
			return false;
		}
	}

	@Override
	public Codec<? extends ICondition> codec()
	{
		return CODEC;
	}

	public static Codec<OptionalRecipeCondition> CODEC = RecordCodecBuilder.create(
			builder -> builder
					.group(
							Codec.STRING.fieldOf("flag_code").forGetter(orc -> orc.flagCode))
					.apply(builder, OptionalRecipeCondition::new));
}
