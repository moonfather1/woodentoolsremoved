package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber
public class EventForAttacking
{
    @SubscribeEvent
    public static void OnLivingHurt(LivingDamageEvent.Pre event)
    {
        if (event.getSource().getMsgId().equals("mob") || event.getSource().getMsgId().equals("player"))
        {
            DamageSource source = event.getSource();
            if (source.getEntity() instanceof LivingEntity)
            {
                ItemStack stack = ((LivingEntity)source.getEntity()).getMainHandItem();
                if (! stack.isEmpty() && ! (stack.getDescriptionId().startsWith("item.silentgear")) && ! (stack.getItem() instanceof ShovelItem) && stack.getItem() instanceof TieredItem && ((TieredItem)stack.getItem()).getTier().equals(Tiers.WOOD))
                {
                    if (event.getSource().getMsgId().equals("player"))
                    {
                        event.setNewDamage(1f);
                        ((Player)source.getEntity()).displayClientMessage(woodenToolMessage, true);
                    }
                    else
                    {
                        event.setNewDamage(event.getOriginalDamage() + OptionsHolder.COMMON.WoodenToolDamageInMobHands.get());
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public static void OnLivingHurt2(LivingDamageEvent.Post event)
//    {
//        if (event.getSource().getMsgId().equals("mob") || event.getSource().getMsgId().equals("player"))
//        {
//            System.out.println("~~~OnLivingHurt2:" + event.getSource().getEntity() + " dealt " + event.getNewDamage() + " dmg (orig==" + event.getOriginalDamage() + ")");
//        }
//    }


    private static final Component woodenToolMessage = Component.translatable("message.woodentoolsremoved.sword_message_1").withStyle(Style.EMPTY.withColor(0x9e7b4d));
}
