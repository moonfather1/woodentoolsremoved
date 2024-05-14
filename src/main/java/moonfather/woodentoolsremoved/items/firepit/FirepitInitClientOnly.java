package moonfather.woodentoolsremoved.items.firepit;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
