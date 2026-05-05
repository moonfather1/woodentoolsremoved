package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.items.FlintToolTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class PickItem extends Item
{
	public PickItem()
	{
		super(FlintToolTier.getInstance(), PickItem.GetProperties());
	}

	private static Item.Properties GetProperties()
	{
		Item i = Items.IRON_PICKAXE;
		Item.Properties properties = new Item.Properties();
		// properties.durability(12); comes from tier now
		properties.attributes(DiggerItem.createAttributes(FlintToolTier.getInstance(), 1.0F, -3.0F));
		return properties;
	}
}
