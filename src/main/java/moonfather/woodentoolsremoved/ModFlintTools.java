package moonfather.woodentoolsremoved;

import com.electronwill.nightconfig.core.file.FileConfig;
import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.items.OptionalRecipeCondition;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import moonfather.woodentoolsremoved.other.dynamic_datapack.EventForDatapack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.NightConfigWrapper;

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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventForDatapack::addServerPack);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        CraftingHelper.register(new OptionalRecipeCondition.Serializer(new ResourceLocation(Constants.MODID, "optional")));
        event.enqueueWork(ToolStatistics::ChangeDurabilityValues);
    }
}
