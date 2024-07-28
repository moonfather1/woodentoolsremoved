package moonfather.woodentoolsremoved.items.javelin;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ThrownJavelinProjectileInitClientOnly
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		EntityRenderers.register(RegistryManager.ThrownJavelinProjectileET.get(), ThrownJavelinProjectileRenderer::new);

		ItemProperties.register(RegistryManager.ItemJavelin.get(), ResourceLocation.withDefaultNamespace("throwing"), (p_234996_, p_234997_, p_234998_, p_234999_) -> {
			return p_234998_ != null && p_234998_.isUsingItem() && p_234998_.getUseItem() == p_234996_ ? 1.0F : 0.0F;
		});
	}


	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ThrownJavelinProjectileRenderer.JavelinModel.LAYER_LOCATION,  ThrownJavelinProjectileRenderer.JavelinModel::createBodyLayer);
	}
}
