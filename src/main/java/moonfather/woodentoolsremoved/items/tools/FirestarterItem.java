package moonfather.woodentoolsremoved.items.tools;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FirestarterItem extends FlintAndSteelItem
{
    public FirestarterItem()
    {
        super((new Properties()).durability(6).setNoRepair());
    }



    @Override
    public int getUseDuration(ItemStack itemStack)
    {
        return 2*20 + ((itemStack.getDamageValue() * 7) % 5 ) * 20;  // 2-6 sec
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_)
    {
        return UseAnim.BRUSH;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level)
    {
        return super.getEntityLifespan(itemStack, level);
    }

    private HitResult calculateHitResult(Player player)
    {
        return ProjectileUtil.getHitResultOnViewVector(
                player, entity -> !entity.isSpectator() && entity.isPickable(), Player.getPickRange(player.isCreative()) * 1.0D      // reduced range moved to useOn
        );
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticksRemaining)
    {
        if (ticksRemaining >= 0 && entity instanceof Player player)
        {
            HitResult hitresult = this.calculateHitResult(player);
            if (hitresult instanceof BlockHitResult blockhitresult && hitresult.getType() == HitResult.Type.BLOCK)
            {
                if (blockhitresult.distanceTo(player) < Constants.GAMEPLAY_FIRESTARTER_RANGE)
                {
                    boolean doParticles = ticksRemaining < 5;        // ticksRemaining % 12 == 0 || ticksRemaining == 3;
                    if (doParticles)
                    {
                        Vec3 forward = player.position().vectorTo(blockhitresult.getBlockPos().getCenter()).normalize();
                        for (int i = 0; i < 3; i++)
                        {
                            double d1 = level.random.nextFloat() * 0.1 + 0.05 * (level.random.nextBoolean()?1:-1);
                            double d2 = level.random.nextFloat() * 0.1 + 0.05 * (level.random.nextBoolean()?1:-1);
                            double d3 = level.random.nextFloat() * 0.1 + 0.05 * (level.random.nextBoolean()?1:-1);
                            level.addParticle(ParticleTypes.SMALL_FLAME, player.position().x + forward.x, player.position().y + level.random.nextFloat() + 0.5, player.position().z + forward.z, d1, d2, d3);
                        }
                        //sound?
                    }
                    return; // success, don't stop
                }
            }
        }
        entity.releaseUsingItem(); // stopping.
    }


    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity)
    {
        livingEntity.releaseUsingItem();
        if (livingEntity instanceof Player player)
        {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
            UseOnContext context = new UseOnContext(player, player.getUsedItemHand(), blockhitresult);
            super.useOn(context);
        }
        return itemStack;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT))
        {
            return InteractionResult.PASS;
        }
        Player player = context.getPlayer();
        BlockHitResult blockhitresult = getPlayerPOVHitResult(context.getLevel(), player, ClipContext.Fluid.ANY);
        if (blockhitresult.distanceTo(player) > Constants.GAMEPLAY_FIRESTARTER_RANGE)
        {
            player.displayClientMessage(ERROR_TOO_FAR, true);
            return InteractionResult.PASS;
        }
        else
        {
            if (player != null && ! player.isSpectator() && blockhitresult.getType() == HitResult.Type.BLOCK)
            {
                player.startUsingItem(context.getHand());
            }
            return InteractionResult.CONSUME;
        }
    }
    private static final Component ERROR_TOO_FAR = Component.translatable("item.woodentoolsremoved.firestarter.error").withColor(Constants.COLOR_MESSAGE_GRAY);

    /////////////////////

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags)
    {
        super.appendHoverText(stack, level, lines, flags);
        lines.add(TooltipForFirestarterLine1);
        lines.add(TooltipForFirestarterLine2);
    }
    private static final Component TooltipForFirestarterLine1 = Component.translatable("item.woodentoolsremoved.firestarter.tooltip1").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));
    private static final Component TooltipForFirestarterLine2 = Component.translatable("item.woodentoolsremoved.firestarter.tooltip2").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));
}
