package moonfather.woodentoolsremoved.items.firepit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;

public class FirepitRenderer implements BlockEntityRenderer<CampfireBlockEntity>
{
    public FirepitRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    public void render(CampfireBlockEntity campfireBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay)
    {
        Direction direction = campfireBlockEntity.getBlockState().getValue(CampfireBlock.FACING);
        NonNullList<ItemStack> nonnulllist = campfireBlockEntity.getItems();
        int i = (int)campfireBlockEntity.getBlockPos().asLong();
        if (campfireBlockEntity.getItems().size() > 1) {
            for (int j = 0; j < nonnulllist.size(); ++j) {
                ItemStack itemstack = nonnulllist.get(j);
                if (itemstack != ItemStack.EMPTY) {
                    poseStack.pushPose();
                    poseStack.translate(0.5D, 0.44921875D, 0.5D);
                    Direction direction1 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                    float f = -direction1.toYRot();
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(f));
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    poseStack.translate(-0.3125D, -0.3125D, 0.0D);
                    poseStack.scale(0.375F, 0.375F, 0.375F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, buffer, i + j);
                    poseStack.popPose();
                }
            }
            // 4 item campfire
        }
        else
        {
            // 1 item campfire
            ItemStack itemstack = campfireBlockEntity.getItems().get(0);
            if (itemstack != ItemStack.EMPTY)
            {
                poseStack.pushPose();
                //poseStack.translate(0.5D, 0.44921875D, 0.5D);              //poseStack.translate(0.5D, 0.44921875D, 0.5D);
                float f = -direction.toYRot();     // maybe we should just random dir?
                poseStack.mulPose(Vector3f.YP.rotationDegrees(f));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                if (direction.getAxis().equals(Direction.Axis.Z))
                {
                    poseStack.translate(0.0d + direction.getStepZ() * +0.5d, direction.getStepZ() * +0.5D, -10 / 16D - 1 / 64d);   //poseStack.translate(-0.3125D, -0.3125D, 0.0D);     // Z is height    y0.75->0
                }
                else
                {
                    poseStack.translate(direction.getStepX() * -0.5, direction.getStepX() * +0.5, -10 / 16D - 1 / 64d);
                }
                poseStack.scale(0.5F, 0.5F, 0.5F);     //was 0.375
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, buffer, i);
                poseStack.popPose();
            }
        }
    }
}
