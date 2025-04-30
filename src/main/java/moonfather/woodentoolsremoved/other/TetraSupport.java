package moonfather.woodentoolsremoved.other;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class TetraSupport
{
    public static final String DoubleToolId = "tetra:modular_double";
    public static final ResourceLocation DoubleToolRL = new ResourceLocation(DoubleToolId);
    private static final Map<Integer, Boolean> toolsCached = new HashMap<>();

    public static boolean IsWoodenTetraTool(ItemStack tool)
    {
        CompoundTag tag = tool.getTag();
        if (tag == null || tag.isEmpty()) return false;
        Boolean cached = toolsCached.get(tag.hashCode());
        if (cached != null)
        {
            return cached;
        }

        boolean gotWood = false;
        String mat = tag.getString("double/basic_pickaxe_right_material");
        gotWood = mat.equals("basic_pickaxe/oak");
        gotWood = gotWood || mat.equals("basic_pickaxe/birch") || mat.equals("basic_pickaxe/dark_oak") || mat.equals("basic_pickaxe/acacia") || mat.equals("basic_pickaxe/spruce") || mat.equals("basic_pickaxe/jungle"); // it can't really be anything other than oak
        if (!gotWood)
        {
            mat = tag.getString("double/basic_pickaxe_left_material");
            gotWood = mat.equals("basic_pickaxe/oak");
            gotWood = gotWood || mat.equals("basic_pickaxe/birch") || mat.equals("basic_pickaxe/dark_oak") || mat.equals("basic_pickaxe/acacia") || mat.equals("basic_pickaxe/spruce") || mat.equals("basic_pickaxe/jungle"); // it can't really be anything other than oak
        }
        if (!gotWood)
        {
            mat = tag.getString("double/basic_axe_left_material");
            gotWood = mat.equals("basic_axe/oak");
            gotWood = gotWood || mat.equals("basic_axe/birch") || mat.equals("basic_axe/dark_oak") || mat.equals("basic_axe/acacia") || mat.equals("basic_axe/spruce") || mat.equals("basic_axe/jungle"); // it can't really be anything other than oak
        }
        if (!gotWood)
        {
            mat = tag.getString("double/basic_axe_right_material");
            gotWood = mat.equals("basic_axe/oak");
            gotWood = gotWood || mat.equals("basic_axe/birch") || mat.equals("basic_axe/dark_oak") || mat.equals("basic_axe/acacia") || mat.equals("basic_axe/spruce") || mat.equals("basic_axe/jungle"); // it can't really be anything other than oak
        }
        if (!gotWood)
        {
            mat = tag.getString("double/adze_left_material");
            gotWood = mat.equals("adze/oak");
            gotWood = gotWood || mat.equals("adze/birch") || mat.equals("adze/dark_oak") || mat.equals("adze/acacia") || mat.equals("adze/spruce") || mat.equals("adze/jungle"); // it can't really be anything other than oak
        }
        if (!gotWood)
        {
            mat = tag.getString("double/adze_right_material");
            gotWood = mat.equals("adze/oak");
            gotWood = gotWood || mat.equals("adze/birch") || mat.equals("adze/dark_oak") || mat.equals("adze/acacia") || mat.equals("adze/spruce") || mat.equals("adze/jungle"); // it can't really be anything other than oak
        }
        toolsCached.put(tag.hashCode(), gotWood);
        return gotWood;
    }

    public static boolean IsTetraFlintItem(ItemStack stack)
    {
        if (!ModList.get().isLoaded("tetra"))
        {
            return false;
        }
        if (ForgeRegistries.ITEMS.getKey(stack.getItem()).equals(DoubleToolRL))
        {
            CompoundTag tag = stack.getTag();
            if (tag == null || tag.isEmpty()) return false;
            boolean gotFlint = tag.getString("double/basic_pickaxe_right_material").equals("basic_pickaxe/flint");
            gotFlint = gotFlint || tag.getString("double/basic_pickaxe_left_material").equals("basic_pickaxe/flint");
            gotFlint = gotFlint || tag.getString("double/basic_axe_left_material").equals("basic_axe/flint");
            gotFlint = gotFlint || tag.getString("double/basic_axe_right_material").equals("basic_axe/flint");
            gotFlint = gotFlint || tag.getString("double/adze_left_material").equals("adze/flint");
            gotFlint = gotFlint || tag.getString("double/adze_right_material").equals("adze/flint");
			return gotFlint;
        }
        return false;
    }
}
