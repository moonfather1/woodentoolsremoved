package moonfather.woodentoolsremoved.original_tools;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ToolStatistics
{
    public static void ChangeDurabilityValues()
    {
        Items.WOODEN_AXE.maxDamage = 6;
        Items.WOODEN_PICKAXE.maxDamage = 6;
        Items.WOODEN_SWORD.maxDamage = 6;
        Items.WOODEN_SHOVEL.maxDamage = 18;
        Items.WOODEN_HOE.maxDamage = 18;

        float newStoneDurabilityFloat = (OptionsHolder.COMMON.StoneToolsDurabilityMultiplier.get() / 100f) * Tiers.STONE.getUses();
        int newStoneDurability = (int)Math.max(newStoneDurabilityFloat, 1f);
        Items.STONE_AXE.maxDamage = newStoneDurability;
        Items.STONE_PICKAXE.maxDamage = newStoneDurability;
        Items.STONE_SWORD.maxDamage = newStoneDurability;
        Items.STONE_SHOVEL.maxDamage = newStoneDurability;
        Items.STONE_HOE.maxDamage = newStoneDurability;

        ToolStatistics.BluntItemAttackModifier((DiggerItem)Items.WOODEN_AXE);
        ToolStatistics.BluntItemAttackModifier((DiggerItem)Items.WOODEN_PICKAXE);
        ToolStatistics.BluntItemAttackModifier((SwordItem)Items.WOODEN_SWORD);
    }

    private static void BluntItemAttackModifier(DiggerItem item)
    {
        (item).getDefaultAttributeModifiers(EquipmentSlot.MAINHAND).forEach(
                (a, am) ->
                {
                    if (ForgeRegistries.ATTRIBUTES.getKey(a).toString().equals("minecraft:generic.attack_damage"))
                    {
                        am.amount = 0f;
                    }
                    //LOGGER.info("~!~!~  " + item + "attribute: " + a.getRegistryName() + " modifier: " + am.getName() + " value  " + am.getAmount() + " (" + am.getOperation() + ")");
                    // ~!~!~  attribute: minecraft:generic.attack_damage modifier: Tool modifier value  6.0 (ADDITION)
                } );
    }

    private static void BluntItemAttackModifier(SwordItem item)
    {
        (item).getDefaultAttributeModifiers(EquipmentSlot.MAINHAND).forEach(
                (a, am) ->
                {
                    if (ForgeRegistries.ATTRIBUTES.getKey(a).toString().equals("minecraft:generic.attack_damage"))
                    {
                        am.amount = 0f;
                    }
                    //LOGGER.info("~!~!~  " + item + "attribute: " + a.getRegistryName() + " modifier: " + am.getName() + " value  " + am.getAmount() + " (" + am.getOperation() + ")");
                    // ~!~!~  attribute: minecraft:generic.attack_damage modifier: Tool modifier value  6.0 (ADDITION)
                } );
    }
}
