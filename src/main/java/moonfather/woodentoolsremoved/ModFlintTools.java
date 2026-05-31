package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import moonfather.woodentoolsremoved.other.Aliasing;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MODID)
public class ModFlintTools
{
    public ModFlintTools(IEventBus modEventBus, ModContainer modContainer)
    {
        // + alias firepit_block to firepit
        // +  adva bg
        // adva maybe initial toast... not sure
        // adva puncher     ... maybe it's fine
        // later - tetra, tc, overgeared, spartan's, insane's...
        // 4 tags     .. later
        modContainer.registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        RegistryManager.init(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(EventForCreativeTabs::onCreativeModeTab);
        modEventBus.addListener(ToolStatistics::onDefaultComponentCreation);
        NeoForge.EVENT_BUS.addListener(ToolStatistics::onItemAttributeQuery);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Aliasing.setUpAliasesForChangedIds();
    }
}
