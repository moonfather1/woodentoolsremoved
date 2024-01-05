package moonfather.woodentoolsremoved.items.tools;


import moonfather.woodentoolsremoved.peaceful.PeacefulGameplaySupport;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HatchetItem extends AxeItem
{
	public HatchetItem()
	{
		super(Tiers.STONE, 3/*dmg*/, -2.8f/*attackspeed*/, HatchetItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Item.Properties properties = new Properties();
		properties.durability(12);
		return properties;
	}



	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags)
	{
		super.appendHoverText(stack, level, lines, flags);
		if (level != null && level.getDifficulty().equals(Difficulty.PEACEFUL) && PeacefulGameplaySupport.HaveCoalDust())
		{
			lines.add(TooltipForPeacefulLine1);
			lines.add(TooltipForPeacefulLine2);
		}
	}

	private static final Component TooltipForPeacefulLine1 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip1").withStyle(Style.EMPTY.withColor(0xd0a949));
	private static final Component TooltipForPeacefulLine2 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip2").withStyle(Style.EMPTY.withColor(0xd0a949));
}
