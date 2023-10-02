package moonfather.woodentoolsremoved.items.tools;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;

public class PickItem extends PickaxeItem
{
	public PickItem()
	{
		super(Tiers.STONE, 1/*dmg*/, -2.8f/*attackspeed*/, PickItem.GetProperties());
	}

	private static Properties GetProperties()
	{
		Properties properties = new Properties();
		properties.durability(12);
		return properties;
	}
}
