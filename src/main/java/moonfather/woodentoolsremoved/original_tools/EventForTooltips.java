package moonfather.woodentoolsremoved.original_tools;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@Mod.EventBusSubscriber
public class EventForTooltips
{
	@SubscribeEvent
	public static void OnItemTooltip(ItemTooltipEvent event)
	{
		if (! event.getItemStack().isEmpty() && (event.getItemStack().getItem().equals(Items.WOODEN_AXE) || event.getItemStack().getItem().equals(Items.WOODEN_PICKAXE) || event.getItemStack().getItem().equals(Items.WOODEN_SWORD)))
		{
			event.getToolTip().add(woodenToolInfo1);
			event.getToolTip().add(woodenToolInfo2);
		}
	}

	private static final Component woodenToolInfo1 = Component.translatable("item.woodentoolsremoved.original.tooltip1").withStyle(Style.EMPTY.withColor(0x9e7b4d));
	private static final Component woodenToolInfo2 = Component.translatable("item.woodentoolsremoved.original.tooltip2").withStyle(Style.EMPTY.withColor(0x9e7b4d));
}
