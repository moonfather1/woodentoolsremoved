package moonfather.woodentoolsremoved.other;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.HashMap;
import java.util.Map;

public class TetraSupport
{
    public static final String DoubleToolId = "tetra:modular_double";
    private static final Map<Integer, Boolean> toolsCached = new HashMap<>();

    public static boolean IsWoodenTetraTool(ItemStack tool)
    {
        return false;         //todo: disabled
    }

    public static boolean IsTetraFlintItem(ItemStack stack)
    {
        if (! ModList.get().isLoaded("tetra"))
        {
            return false;
        }
        if (BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().equals(DoubleToolId))
        {
//            CompoundTag tag = stack.getTag();
//            boolean gotFlint = tag.getString("double/basic_pickaxe_right_material").equals("basic_pickaxe/flint");
//            gotFlint = gotFlint || tag.getString("double/basic_pickaxe_left_material").equals("basic_pickaxe/flint");
//            gotFlint = gotFlint || tag.getString("double/basic_axe_left_material").equals("basic_axe/flint");
//            gotFlint = gotFlint || tag.getString("double/basic_axe_right_material").equals("basic_axe/flint");
//            gotFlint = gotFlint || tag.getString("double/adze_left_material").equals("adze/flint");
//            gotFlint = gotFlint || tag.getString("double/adze_right_material").equals("adze/flint");
//            return gotFlint;
            return false;     // todo disabled
        }
        return false;
    }
}
