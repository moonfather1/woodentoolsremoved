package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

public class HatchetItem extends AxeItem
{
	public HatchetItem(ResourceKey<Item> id)
	{
		super(Constants.Materials.FLINT, 2.0F, -3.0F, HatchetItem.makeProperties(id));
	}

	private static Properties makeProperties(ResourceKey<Item> id)
	{
		Item.Properties properties = new Properties();
		properties.durability(12);
		properties.setId(id);
		return properties;
	}
}
