package moonfather.woodentoolsremoved.logs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ToolResolver
{
    private static final Map<Integer, Boolean> toolsCached = new HashMap<>();

    public static boolean isWoodenAxe(ItemStack stack)
    {
        if (! (stack.getItem() instanceof AxeItem))
        {
            return false;
        }
        int key = stack.hashCode() + 101;
        Boolean cached = toolsCached.get(key);
        if (cached != null)
        {
            return cached;
        }

        if (stack.getOrCreateTag().contains("SGear_Data"))
        {
            boolean result = stack.getTag().getCompound("SGear_Data").getCompound("Properties").getCompound("Stats").getFloat("silentgear:harvest_level") == 0f;
            toolsCached.put(key, result);
            return result;
        }
        boolean result = ((AxeItem) stack.getItem()).getTier().equals(Tiers.WOOD);
        toolsCached.put(key, result);
        return result;
    }



    public static boolean isWoodenPickaxe(ItemStack stack)
    {
        int key = stack.hashCode() + 2002;
        Boolean cached = toolsCached.get(key);
        if (cached != null)
        {
            return cached;
        }

        if (stack.canPerformAction(ToolActions.PICKAXE_DIG) && stack.getTag().contains("tic_stats"))
        {
            boolean result = stack.getTag().getCompound("tic_stats").getString("tconstruct:harvest_tier").equals("minecraft:wood")
                    || stack.getTag().getList("tic_materials", 8).getString(0).contains("wood");
            toolsCached.put(key, result);
            return result;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id != null && id.getNamespace().equals("minecraft"))
        {
            boolean result = stack.getItem() instanceof PickaxeItem pick && pick.getTier().equals(Tiers.WOOD);
            toolsCached.put(key, result);
            return result;
        }
        boolean result = stack.getItem() instanceof PickaxeItem pick && ! stack.isCorrectToolForDrops(Blocks.IRON_ORE.defaultBlockState()) && ! pick.getTier().equals(Tiers.GOLD);
        toolsCached.put(key, result);
        return result;
    }



    public static boolean isFlintPickaxe(ItemStack stack)
    {
        int key = stack.hashCode() + 30003;
        Boolean cached = toolsCached.get(key);
        if (cached != null)
        {
            return cached;
        }

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id != null && id.getNamespace().equals("silentgear"))
        {
            boolean result = false; // resolved in another manner
            toolsCached.put(key, result);
            return result;
        }
        if (id != null && id.toString().equals("tconstruct:pickaxe"))
        {
            boolean result = stack.getTag().getList("tic_materials", 8).getString(0).contains("flint")
                    || stack.getTag().getList("tic_materials", 8).getString(0).contains("bone");
            toolsCached.put(key, result);
            return result;
        }
        boolean result = false;
        toolsCached.put(key, result);
        return result;
    }

}
