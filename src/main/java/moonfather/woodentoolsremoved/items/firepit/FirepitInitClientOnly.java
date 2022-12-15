package moonfather.woodentoolsremoved.items.firepit;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FirepitInitClientOnly
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		ItemBlockRenderTypes.setRenderLayer(RegistryManager.BlockFirepit.get(), RenderType.cutoutMipped());
	}


	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(BlockEntityType.CAMPFIRE,	FirepitRenderer::new);
	}
}
