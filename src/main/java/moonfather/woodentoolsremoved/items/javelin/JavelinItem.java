package moonfather.woodentoolsremoved.items.javelin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import moonfather.woodentoolsremoved.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JavelinItem extends TridentItem
{
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public JavelinItem()
    {
        super(JavelinItem.GetProperties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-3.0F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    private static Properties GetProperties()
    {
        Item.Properties properties = new Properties();
        properties.durability(6);
        properties.tab(CreativeModeTab.TAB_COMBAT);
        return properties;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public int getEnchantmentValue() {
        return 0;
    }

    /// copy-pasta:

    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player) {
            Player player = (Player)p_43396_;
            int i = this.getUseDuration(p_43394_) - p_43397_;
            if (i >= 10) {
                if (!p_43395_.isClientSide) {
                    p_43394_.hurtAndBreak(1, player, (p_43388_) -> {
                        p_43388_.broadcastBreakEvent(p_43396_.getUsedItemHand());
                    });

                    ThrownJavelinProjectile throwntrident = new ThrownJavelinProjectile(p_43395_, player, p_43394_);
                    throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    p_43395_.addFreshEntity(throwntrident);
                    p_43395_.playSound((Player)null, throwntrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(p_43394_);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    ///////////// info ///////////////////////

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags)
    {
        super.appendHoverText(stack, level, lines, flags);
        if (stack.getDamageValue() == stack.getMaxDamage() - 1)
        {
            lines.add(line1);
        }
    }

    private static final Component line1 = Component.translatable("item.woodentoolsremoved.javelin1dur.tooltip").withStyle(Style.EMPTY.withColor(Constants.COLOR_WARNING_TOOLTIPS));
}
