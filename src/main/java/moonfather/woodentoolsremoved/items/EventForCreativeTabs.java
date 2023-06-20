package moonfather.woodentoolsremoved.items;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;


public class EventForCreativeTabs
{
	public static void OnCreativeModeTab(BuildCreativeModeTabContentsEvent event)
	{
		if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES))
		{
			event.accept(RegistryManager.ItemHatchet.get());
			event.accept(RegistryManager.ItemMiniPick.get());
		}
		if (event.getTabKey().equals(CreativeModeTabs.COMBAT))
		{
			event.accept(RegistryManager.ItemJavelin.get());
		}
		if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS))
		{
			event.accept(RegistryManager.ItemFirepit.get());
			event.accept(RegistryManager.ItemBlackPowderBowl.get());
		}
	}
}
