package moonfather.woodentoolsremoved.items.javelin;

import com.google.common.collect.ImmutableMultimap;
import moonfather.woodentoolsremoved.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class JavelinItem extends TridentItem
{
    public JavelinItem(ResourceKey<Item> key)
    {
        super(JavelinItem.getProperties().setId(key));
    }

    private static Properties getProperties()
    {
        Item.Properties properties = new Properties();
        properties.durability(6);

        ItemAttributeModifiers attributeModifiers =  ItemAttributeModifiers.builder()
                                   .add(
                                             Attributes.ATTACK_DAMAGE,
                                             new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 5.0D, AttributeModifier.Operation.ADD_VALUE),
                                             EquipmentSlotGroup.MAINHAND
                                   )
                                   .add(
                                             Attributes.ATTACK_SPEED,
                                             new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.0D, AttributeModifier.Operation.ADD_VALUE),
                                             EquipmentSlotGroup.MAINHAND
                                   )
                                   .build();

        properties.attributes(attributeModifiers);
        return properties;
    }



    /// copy-pasta:


    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int remainingTime)
    {
        if (livingEntity instanceof Player player) {
            int timeHeld = this.getUseDuration(itemStack, livingEntity) - remainingTime;
            if (timeHeld >= 10) {
                if ( !level.isClientSide()) {
                    itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                    ThrownJavelinProjectile throwntrident = new ThrownJavelinProjectile(level, player, itemStack);
                    throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
                    if (player.hasInfiniteMaterials()) {
                        throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(throwntrident);
                    level.playSound((Player)null, throwntrident, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (! player.hasInfiniteMaterials()) {
                        player.getInventory().removeItem(itemStack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return false;
    }

    ///////////// info ///////////////////////

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        if (itemStack.getDamageValue() == itemStack.getMaxDamage() - 1)
        {
            builder.accept(line1);
        }
    }

    private static final Component line1 = Component.translatable("item.woodentoolsremoved.javelin1dur.tooltip").withStyle(Style.EMPTY.withColor(Constants.COLOR_WARNING_TOOLTIPS));
}
