package moonfather.woodentoolsremoved.items.firepit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FirepitRenderer implements BlockEntityRenderer<CampfireBlockEntity, FirepitRenderer.FirepitRenderState>
{
    public FirepitRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }
    private final ItemModelResolver itemModelResolver;

    /////////////////////////////////////////

    @NotNull
    public static class FirepitRenderState extends BlockEntityRenderState
    {
        //public ItemStackRenderState[] items = new ItemStackRenderState[1];
        public ItemStackRenderState item = new ItemStackRenderState();
        public Direction direction = Direction.EAST;
        public int numberOfItems;
    }

    @Override
    public FirepitRenderState createRenderState()
    {
        return new FirepitRenderState();
    }

    @Override
    public void extractRenderState(CampfireBlockEntity blockEntity, FirepitRenderState renderState, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress)
    {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTicks, cameraPosition, breakProgress);
        // and now fill in our stuff
        int seedBase = Long.valueOf(blockEntity.getBlockPos().asLong()).hashCode();
        renderState.direction = blockEntity.getBlockState().getValue(CampfireBlock.FACING);
        renderState.numberOfItems = 1;  // blockEntity.items.size(); is forced to be 1
        int i = 1; // if i add larger later
        if (! blockEntity.items.get(0).isEmpty())
        {
            ItemStackRenderState itemState = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(itemState, blockEntity.items.get(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seedBase + i);
            renderState.item = itemState;
        }
        else
        {
            renderState.item = null;
        }
    }

    @Override
    public void submit(FirepitRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState camera)
    {
        Direction direction = state.direction;;
        ItemStackRenderState itemStack = state.item;
        if (itemStack != null)
        {
            poseStack.pushPose();
            //poseStack.translate(0.5D, 0.44921875D, 0.5D);              //poseStack.translate(0.5D, 0.44921875D, 0.5D);
            float f = -direction.toYRot();     // maybe we should just random dir?
            poseStack.mulPose(Axis.YP.rotationDegrees(f));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            if (direction.getAxis().equals(Direction.Axis.Z))
            {
                poseStack.translate(0.0d + direction.getStepZ() * +0.5d, direction.getStepZ() * +0.5D, -10 / 16D - 1 / 64d);   //poseStack.translate(-0.3125D, -0.3125D, 0.0D);     // Z is height    y0.75->0
            }
            else
            {
                poseStack.translate(direction.getStepX() * -0.5, direction.getStepX() * +0.5, -10 / 16D - 1 / 64d);
            }
            poseStack.scale(0.5F, 0.5F, 0.5F);     //was 0.375
            itemStack.submit(poseStack, nodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
