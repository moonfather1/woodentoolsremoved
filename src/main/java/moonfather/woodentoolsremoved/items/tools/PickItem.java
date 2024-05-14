package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.items.FlintToolTier;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.PickaxeItem;

public class PickItem extends PickaxeItem
{
	public PickItem()
	{
		super(FlintToolTier.getInstance(), PickItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Properties properties = new Properties();
		// properties.durability(12); comes from tier now
		properties.attributes(DiggerItem.createAttributes(FlintToolTier.getInstance(), 1.0F, -3.0F));
		return properties;
	}
}
