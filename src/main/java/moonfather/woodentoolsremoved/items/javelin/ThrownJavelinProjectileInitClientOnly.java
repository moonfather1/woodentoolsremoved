package moonfather.woodentoolsremoved.items.javelin;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ThrownJavelinProjectileInitClientOnly
{
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		EntityRenderers.register(RegistryManager.ThrownJavelinProjectileET.get(), ThrownJavelinProjectileRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ThrownJavelinProjectileRenderer.JavelinModel.LAYER_LOCATION,  ThrownJavelinProjectileRenderer.JavelinModel::createLayer);
	}
}
