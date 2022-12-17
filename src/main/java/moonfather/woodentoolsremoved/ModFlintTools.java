package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.EventForCreativeTabs;
import moonfather.woodentoolsremoved.items.OptionalRecipeCondition;
import moonfather.woodentoolsremoved.original_tools.ToolStatistics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MODID)
public class ModFlintTools
{
    public ModFlintTools()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, OptionsHolder.SERVER_SPEC);
        RegistryManager.Init();
        // Register the commonSetup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventForCreativeTabs::OnCreativeModeTab);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        CraftingHelper.register(new OptionalRecipeCondition.Serializer(new ResourceLocation(Constants.MODID, "optional")));
        event.enqueueWork(ToolStatistics::ChangeDurabilityValues);
    }
}
