package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForAttacking
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void OnLivingHurt(LivingHurtEvent event)
    {
        if (event.getSource().getMsgId().equals("mob") || event.getSource().getMsgId().equals("player"))
        {
            DamageSource source = event.getSource();
            if (source.getEntity() instanceof LivingEntity)
            {
                ItemStack stack = ((LivingEntity)source.getEntity()).getMainHandItem();
                if (! stack.isEmpty() && ! (/*stack.getDescriptionId() != null && */stack.getDescriptionId().startsWith("item.silentgear")) && ! (stack.getItem() instanceof ShovelItem) && stack.getItem() instanceof TieredItem ti && (ti.getTier().equals(Tiers.WOOD) || ti.getTier().getRepairIngredient().test(Items.OAK_PLANKS.getDefaultInstance())))
                {
                    if (event.getSource().getMsgId().equals("player"))
                    {
                        event.setAmount(1f);
                        ((Player)source.getEntity()).displayClientMessage(woodenToolMessage, true);
                    }
                    else
                    {
                        event.setAmount(event.getAmount() + OptionsHolder.COMMON.WoodenToolDamageInMobHands.get());
                    }
                }
            }
        }
    }



    private static final Component woodenToolMessage = Component.translatable("message.woodentoolsremoved.sword_message_1").withStyle(Style.EMPTY.withColor(Constants.COLOR_DUD_TOOLTIPS));
}
