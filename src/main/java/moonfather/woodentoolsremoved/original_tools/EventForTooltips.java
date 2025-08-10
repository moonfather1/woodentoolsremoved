package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.logs.ToolResolver;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForTooltips
{
	@SubscribeEvent
	public static void OnItemTooltip(ItemTooltipEvent event)
	{
		if (! event.getItemStack().isEmpty()
				&& (ToolResolver.isWoodenAxe(event.getItemStack())
					|| ToolResolver.isWoodenPickaxe(event.getItemStack())
					|| event.getItemStack().getItem().equals(Items.WOODEN_SWORD)))	
		{
			event.getToolTip().add(woodenToolInfo1);
			event.getToolTip().add(woodenToolInfo2);
		}
	}

	private static final Component woodenToolInfo1 = Component.translatable("item.woodentoolsremoved.original.tooltip1").withStyle(Style.EMPTY.withColor(Constants.COLOR_DUD_TOOLTIPS));
	private static final Component woodenToolInfo2 = Component.translatable("item.woodentoolsremoved.original.tooltip2").withStyle(Style.EMPTY.withColor(Constants.COLOR_DUD_TOOLTIPS));
}
