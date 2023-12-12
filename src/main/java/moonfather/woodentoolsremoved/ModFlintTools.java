package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MODID)
public class ModFlintTools
{
    // todo: when tetra is out for 1.19 and 1.20, update wood materials in data folder
    public ModFlintTools()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        RegistryManager.Init();
        // Register the commonSetup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventForCreativeTabs::OnCreativeModeTab);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(ToolStatistics::ChangeDurabilityValues);
    }
}
