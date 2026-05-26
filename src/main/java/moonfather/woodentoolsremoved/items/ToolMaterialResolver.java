package moonfather.woodentoolsremoved.items;

import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class ToolMaterialResolver
{
    public static boolean isWoodenAxe(ItemStack tool)
    {
        Boolean cached = isAxe.getOrDefault(tool, null);
        if (cached != null) { return cached; }
        if (isAxe.size() > 789) { isAxe.clear(); }
        boolean result = false;
        if (tool.canPerformAction(ItemAbilities.AXE_STRIP) || tool.getItem() instanceof AxeItem)
        {
            Tool component = tool.get(DataComponents.TOOL);
            if (component != null)
            {
                for (Tool.Rule rule : component.rules())
                {
                    if (rule.correctForDrops().isPresent() && rule.blocks() instanceof HolderSet.Named nhs && nhs.key().equals(BlockTags.INCORRECT_FOR_WOODEN_TOOL))
                    {
                        result = rule.correctForDrops().get() == false;
                    }
                }
            }
        }
        isAxe.put(tool, result);
        return result;
        //  ! toolId.getNamespace().equals("silentgear")
    }

    public static boolean isWoodenPickaxe(ItemStack tool)
    {
        Boolean cached = isPickaxe.getOrDefault(tool, null);
        if (cached != null) { return cached; }
        if (isPickaxe.size() > 789) { isPickaxe.clear(); }
        ////////////////////////
        boolean canMineStone = tool.isCorrectToolForDrops(Blocks.STONE.defaultBlockState()); // normally true for any pick (though we've taken that from wooden p via a tag)
        boolean canMineCopperOre = tool.isCorrectToolForDrops(Blocks.COPPER_ORE.defaultBlockState()); // copper need s stone pick or better
        boolean result = canMineStone && ! canMineCopperOre; // this should be enough for most pickaxes.
        if (result && tool.isValidRepairItem(Items.GOLD_INGOT.getDefaultInstance()))
        {
            result = false;  // fix golden pick because above picks it up
        }
        if (! canMineStone && tool.is(ItemTags.PICKAXES))
        {
            Repairable rc = tool.get(DataComponents.REPAIRABLE);
            result = rc != null && rc.items().size() > 0 && rc.items().get(0).is(ItemTags.PLANKS); // anything wooden matches this
        }
        /////////////////////
        isPickaxe.put(tool, result);
        return result;
        //  || toolId.toString().equals("tconstruct:pickaxe") && event.getEntity().getMainHandItem().get(DataComponents.CUSTOM_DATA).getUnsafe().getCompound("tic_stats").getString("tconstruct:harvest_tier").equals("minecraft:wood")
    }

    public static boolean isWoodenWeapon(ItemStack stack)
    {
        Boolean cached = isWeapon.getOrDefault(stack, null);
        if (cached != null) { return cached; }
        if (isWeapon.size() > 789) { isWeapon.clear(); }
        // Tool component1 = stack.get(DataComponents.TOOL); // gets cobweb, bast on bamboo, fast on leaves.... nothing useful.
        Weapon component2 = stack.get(DataComponents.WEAPON);  // nothing useful
        Repairable component3 = stack.get(DataComponents.REPAIRABLE);  // now we're talking
        boolean result = false;
        if (component2 != null // is weapon
                && component3 != null && component3.items().size() > 0 & component3.items().get(0).is(ItemTags.PLANKS))
        {
            result = true;
        }
        isWeapon.put(stack, result);
        return result;
        // ! stack.getDescriptionId().startsWith("item.silentgear")) && ! (stack.getItem() instanceof ShovelItem) && stack.getItem() instanceof TieredItem && ((TieredItem)stack.getItem()).getTier().equals(Tiers.WOOD)
    }
    private  static final Map<ItemStack, Boolean> isAxe = new HashMap<>();
    private  static final Map<ItemStack, Boolean> isPickaxe = new HashMap<>();
    private  static final Map<ItemStack, Boolean> isWeapon = new HashMap<>();
}
