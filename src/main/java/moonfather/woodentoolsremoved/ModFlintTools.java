package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MODID)
public class ModFlintTools
{
    public ModFlintTools(IEventBus modBus)
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        RegistryManager.Init(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(EventForCreativeTabs::OnCreativeModeTab);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ToolStatistics::ChangeDurabilityValues);
    }
}
