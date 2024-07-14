package moonfather.woodentoolsremoved.items.javelin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import moonfather.woodentoolsremoved.Constants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownJavelinProjectileRenderer extends EntityRenderer<ThrownJavelinProjectile>
{
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("woodentoolsremoved:textures/entity/javelin.png");
	private final JavelinModel model;

	public ThrownJavelinProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new JavelinModel(context.bakeLayer(JavelinModel.LAYER_LOCATION));
	}

	public void render(ThrownJavelinProjectile p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
		p_116114_.pushPose();
		p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 90.0F));
		p_116114_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot()) + 90.0F));
		VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(this.getTextureLocation(p_116111_)), false, false/*foil*/);
		this.model.renderToBuffer(p_116114_, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_116114_.popPose();
		super.render(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
	}

	public ResourceLocation getTextureLocation(ThrownJavelinProjectile p_116109_) {
		return TEXTURE_LOCATION;
	}

	///////////////////////////////////////

	public class JavelinModel extends EntityModel<ThrownJavelinProjectile> {
		// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Constants.MODID, "javelin"), "main");
		private final ModelPart bb_main;

		public JavelinModel(ModelPart root) {
			this.bb_main = root.getChild("bb_main");
		}

		public static LayerDefinition createBodyLayer() {
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

		@Override
		public void setupAnim(ThrownJavelinProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		}

		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}