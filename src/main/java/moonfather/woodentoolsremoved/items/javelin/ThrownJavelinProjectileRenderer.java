package moonfather.woodentoolsremoved.items.javelin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import moonfather.woodentoolsremoved.Constants;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

public class ThrownJavelinProjectileRenderer extends EntityRenderer<ThrownJavelinProjectile, ThrownTridentRenderState>
{
	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(Constants.MODID, "textures/entity/javelin.png");
	private final JavelinModel model;

	public ThrownJavelinProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new JavelinModel(context.bakeLayer(JavelinModel.LAYER_LOCATION));
	}



	@Override
	public void submit(ThrownTridentRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot + 90.0F));
		submitNodeCollector.order(0)
				.submitModel(this.model, Unit.INSTANCE, poseStack, TEXTURE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);
	}


	@Override
	public ThrownTridentRenderState createRenderState() {
		return new ThrownTridentRenderState();
	}

	@Override
	public void extractRenderState(ThrownJavelinProjectile entity, ThrownTridentRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.isFoil = false;
	}

////////////////////////////////////////////////////////////////////

	public static class JavelinModel extends Model<Unit>
	{
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Constants.MODID, "javelin"), "main");

		public JavelinModel(ModelPart root)
		{
			super(root, RenderTypes::entitySolid);
		}

		public static LayerDefinition createLayer()
		{
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -26.0F, 0.0F, 1.0F, 26.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(6, 14).addBox(-2.0F, -30.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(6, 27).addBox(-1.0F, -32.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(12, 30).addBox(-2.0F, -31.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(13, 25).addBox(-3.0F, -29.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(13, 17).addBox(0.0F, -26.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
					.texOffs(14, 8).addBox(1.0F, -28.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
			return LayerDefinition.create(meshdefinition, 32, 32);
		}
	}
}