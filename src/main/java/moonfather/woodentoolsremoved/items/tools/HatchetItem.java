package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.items.FlintToolTier;
import moonfather.woodentoolsremoved.other.ClientLevelAccessor;
import moonfather.woodentoolsremoved.peaceful.PeacefulGameplaySupport;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class HatchetItem extends AxeItem
{
	public HatchetItem()
	{
		super(FlintToolTier.getInstance(), HatchetItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Item.Properties properties = new Properties();
		// properties.durability(12); comes from tier now
		properties.attributes(DiggerItem.createAttributes(FlintToolTier.getInstance(), 2.0F, -3.0F));
		return properties;
	}



	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> lines, TooltipFlag flag)
	{
		super.appendHoverText(stack, tooltipContext, lines, flag);
		if (ClientLevelAccessor.isPeaceful() && PeacefulGameplaySupport.HaveCoalDust())
		{
			lines.add(TooltipForPeacefulLine1);
			lines.add(TooltipForPeacefulLine2);
		}
	}

	private static final Component TooltipForPeacefulLine1 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip1").withStyle(Style.EMPTY.withColor(0xd0a949));
	private static final Component TooltipForPeacefulLine2 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip2").withStyle(Style.EMPTY.withColor(0xd0a949));
}
