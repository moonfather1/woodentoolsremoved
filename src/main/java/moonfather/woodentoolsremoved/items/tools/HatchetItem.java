package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.items.FlintToolTier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

public class HatchetItem extends AxeItem
{
	public HatchetItem(ResourceKey<Item> id)
	{
		super(FlintToolTier.FLINT_HATCHET, 2.0F, -3.0F, HatchetItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Item.Properties properties = new Properties();
		properties.durability(12);
		return properties;
	}
}
