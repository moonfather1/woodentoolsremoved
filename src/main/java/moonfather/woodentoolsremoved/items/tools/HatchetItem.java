package moonfather.woodentoolsremoved.items.tools;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HatchetItem extends AxeItem
{
	public HatchetItem()
	{
		super(Tiers.STONE, 4/*dmg*/, -2.5f/*attackspeed*/, HatchetItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Item.Properties properties = new Properties();
		properties.tab(CreativeModeTab.TAB_TOOLS);
		properties.durability(12);
		return properties;
	}



	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags)
	{
		super.appendHoverText(stack, level, lines, flags);
		if (level != null && level.getDifficulty().equals(Difficulty.PEACEFUL))
		{
			lines.add(TooltipForPeacefulLine1);
			lines.add(TooltipForPeacefulLine2);
		}
	}

	public static final Component TooltipForPeacefulLine1 = new TranslatableComponent("item.woodentoolsremoved.peaceful.tooltip1").withStyle(Style.EMPTY.withColor(0xf46a4e));
	public static final Component TooltipForPeacefulLine2 = new TranslatableComponent("item.woodentoolsremoved.peaceful.tooltip1").withStyle(Style.EMPTY.withColor(0xf46a4e));
}
