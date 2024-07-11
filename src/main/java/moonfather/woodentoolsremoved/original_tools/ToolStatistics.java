package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

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
        if ((event.getItemStack().is(Items.WOODEN_AXE) || event.getItemStack().is(Items.WOODEN_PICKAXE) || event.getItemStack().is(Items.WOODEN_SWORD)))
        {
            boolean hasMinus = false;
            for (ItemAttributeModifiers.Entry modifier : event.getModifiers())
            {
                if (modifier.attribute().equals(Attributes.ATTACK_DAMAGE))
                {
                    if (modifier.modifier().id().equals(mini_plus_id))
                    {
                        hasMinus = true;
                        break;
                    }
                }
            }
            if (! hasMinus)
            {
                event.removeIf(m -> m.attribute().equals(Attributes.ATTACK_DAMAGE) && m.modifier().operation().equals(AttributeModifier.Operation.ADD_VALUE));
                event.addModifier(Attributes.ATTACK_DAMAGE, mini_plus, EquipmentSlotGroup.MAINHAND);
            }
        }
    }

    private static final ResourceLocation mini_plus_id = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "mini_plus_id");
    private static final AttributeModifier mini_plus = new AttributeModifier(mini_plus_id, 0.5d, AttributeModifier.Operation.ADD_VALUE);
}
