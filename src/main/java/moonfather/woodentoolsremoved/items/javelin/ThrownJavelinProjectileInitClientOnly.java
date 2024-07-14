package moonfather.woodentoolsremoved.items.javelin;

import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ThrownJavelinProjectileInitClientOnly
{
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event)
	{
		EntityRenderers.register(RegistryManager.ThrownJavelinProjectileET.get(), ThrownJavelinProjectileRenderer::new);

		ItemProperties.register(RegistryManager.ItemJavelin.get(), new ResourceLocation("throwing"), (p_234996_, p_234997_, p_234998_, p_234999_) -> {
			return p_234998_ != null && p_234998_.isUsingItem() && p_234998_.getUseItem() == p_234996_ ? 1.0F : 0.0F;
		});
	}


	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event)
	{
		event.registerLayerDefinition(ThrownJavelinProjectileRenderer.JavelinModel.LAYER_LOCATION,  ThrownJavelinProjectileRenderer.JavelinModel::createBodyLayer);
	}
}
