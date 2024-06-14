package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MODID)
public class ModFlintTools
{
    public ModFlintTools(IEventBus modEventBus, ModContainer modContainer)
    {

        modContainer.registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        RegistryManager.Init(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(EventForCreativeTabs::OnCreativeModeTab);
        modEventBus.addListener(ToolStatistics::OnDefaultComponentCreation);
        NeoForge.EVENT_BUS.addListener(ToolStatistics::OnItemAttributeQuery);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
}
