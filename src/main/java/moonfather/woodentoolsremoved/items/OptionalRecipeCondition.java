package moonfather.woodentoolsremoved.items;

import com.google.gson.JsonObject;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

public class OptionalRecipeCondition implements ICondition
{
	private final String flagCode;
	private final ResourceLocation conditionId;

	private OptionalRecipeCondition(ResourceLocation id, String value)
	{
		this.conditionId = id;
		this.flagCode = value;
	}

	@Override
	public ResourceLocation getID()
	{
		return this.conditionId;
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
		else
		{
			return false;
		}
	}

	/////////////////////////////////////////////////////

	public static class Serializer implements IConditionSerializer<OptionalRecipeCondition>
	{
		private final ResourceLocation conditionId;

		public Serializer(ResourceLocation id)
		{
			this.conditionId = id;
		}

		@Override
		public void write(JsonObject json, OptionalRecipeCondition condition)
		{
			json.addProperty("flag_code", condition.flagCode);
		}

		@Override
		public OptionalRecipeCondition read(JsonObject json)
		{
			return new OptionalRecipeCondition(this.conditionId, json.getAsJsonPrimitive("flag_code").getAsString());
		}

		@Override
		public ResourceLocation getID()
		{
			return this.conditionId;
		}
	}
}
