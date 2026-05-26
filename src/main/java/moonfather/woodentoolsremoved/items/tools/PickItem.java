package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class PickItem extends Item
{
	public PickItem(ResourceKey<Item> key)
	{
		super(PickItem.getProperties().setId(key));
	}

	private static Item.Properties getProperties()
	{
		Item i = Items.IRON_PICKAXE;
		Item.Properties properties = new Item.Properties();
		properties.pickaxe(Constants.Materials.FLINT, 1.0F, -3.0F);
		properties.durability(6);
		return properties;
	}
}
