package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForAttacking
{
    @SubscribeEvent
    public static void OnLivingHurt(LivingHurtEvent event)
    {
        if (event.getSource().msgId.equals("mob") || event.getSource().msgId.equals("player"))
        {
            EntityDamageSource ds = (EntityDamageSource)event.getSource();
            if (ds.getEntity() instanceof LivingEntity)
            {
                ItemStack stack = ((LivingEntity)ds.getEntity()).getMainHandItem();
                if (! stack.isEmpty() && ! (stack.getItem() instanceof ShovelItem) && stack.getItem() instanceof TieredItem && ((TieredItem)stack.getItem()).getTier().equals(Tiers.WOOD))
                {
                    if (event.getSource().msgId.equals("player"))
                    {
                        event.setAmount(1f);
                        ((Player) ds.getEntity()).displayClientMessage(woodenToolMessage, true);
                    }
                    else
                    {
                        event.setAmount(event.getAmount() + OptionsHolder.COMMON.WoodenToolDamageInMobHands.get());
                    }
                }
            }
        }
    }


    private static final Component woodenToolMessage = Component.translatable("message.woodentoolsremoved.sword_message_1").withStyle(Style.EMPTY.withColor(0x9e7b4d));
}
