package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.UUID;

public class ToolStatistics
{
    public static void OnDefaultComponentCreation(ModifyDefaultComponentsEvent event)
    {
        event.modify(Items.WOODEN_AXE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_PICKAXE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_SWORD, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_SHOVEL, builder -> builder.set(DataComponents.MAX_DAMAGE, 18));
        event.modify(Items.WOODEN_HOE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));

        float newStoneDurabilityFloat = (OptionsHolder.COMMON.StoneToolsDurabilityMultiplier.get() / 100f) * Tiers.STONE.getUses();
        int newStoneDurability = (int)Math.max(newStoneDurabilityFloat, 1f);
        event.modify(Items.STONE_AXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_PICKAXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_SWORD, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_SHOVEL, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_HOE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
    }



    public static void OnItemAttributeQuery(ItemAttributeModifierEvent event)
    {
        if ((event.getItemStack().is(Items.WOODEN_AXE) || event.getItemStack().is(Items.WOODEN_PICKAXE) || event.getItemStack().is(Items.WOODEN_SWORD)) && event.getSlotType().equals(EquipmentSlot.MAINHAND))
        {
            if (event.getModifiers().containsKey(Attributes.ATTACK_DAMAGE))
            {
                boolean hasMinus = false;
                for (AttributeModifier m: event.getModifiers().get(Attributes.ATTACK_DAMAGE))
                {
                    if (m.id().equals(mini_plus_id))
                    {
                        hasMinus = true;
                        break;
                    }
                }
                if (hasMinus) { return; }
                event.getModifiers().get(Attributes.ATTACK_DAMAGE).removeIf(m -> m.operation().equals(AttributeModifier.Operation.ADD_VALUE));
                event.getModifiers().get(Attributes.ATTACK_DAMAGE).add(mini_plus);
            }
        }
    }

    private static final ResourceLocation mini_plus_id = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "mini_plus_id");
    private static final AttributeModifier mini_plus = new AttributeModifier(mini_plus_id, 0.5d, AttributeModifier.Operation.ADD_VALUE);
}
