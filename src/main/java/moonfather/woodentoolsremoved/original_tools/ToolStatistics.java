package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

public class ToolStatistics
{
    public static void onDefaultComponentCreation(ModifyDefaultComponentsEvent event)
    {
        event.modify(Items.WOODEN_AXE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_PICKAXE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_SWORD, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_SHOVEL, builder -> builder.set(DataComponents.MAX_DAMAGE, 18));
        event.modify(Items.WOODEN_HOE, builder -> builder.set(DataComponents.MAX_DAMAGE, 6));
        event.modify(Items.WOODEN_SPEAR, (builder, _, _) -> builder.set(DataComponents.MAX_DAMAGE, 6));// todo replace deprecated with this

        double newStoneDurabilityFloat = (OptionsHolder.COMMON.StoneToolsDurabilityMultiplier.get() / 100d) * ToolMaterial.STONE.durability();
        newStoneDurabilityFloat = Math.floor(newStoneDurabilityFloat);
        int newStoneDurability = (int)Math.max(newStoneDurabilityFloat, 1f);
        event.modify(Items.STONE_AXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_PICKAXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_SWORD, builder -> builder.set(DataComponents.MAX_DAMAGE, (int) (newStoneDurability *0.2)));
        event.modify(Items.STONE_SHOVEL, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_HOE, builder -> builder.set(DataComponents.MAX_DAMAGE, newStoneDurability));
        event.modify(Items.STONE_SPEAR, builder -> builder.set(DataComponents.MAX_DAMAGE, (int) (newStoneDurability *0.8)));
    }



    public static void onItemAttributeQuery(ItemAttributeModifierEvent event)
    {
        if ((event.getItemStack().is(Items.WOODEN_AXE) || event.getItemStack().is(Items.WOODEN_PICKAXE) || event.getItemStack().is(Items.WOODEN_SWORD) || event.getItemStack().is(Items.WOODEN_SPEAR)))
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

    private static final Identifier mini_plus_id = Identifier.fromNamespaceAndPath(Constants.MODID, "mini_plus_id");
    private static final AttributeModifier mini_plus = new AttributeModifier(mini_plus_id, 0.5d, AttributeModifier.Operation.ADD_VALUE);
}
