package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class Aliasing
{
    public static void setUpAliasesForChangedIds()
    {
        Identifier missing, fallback;
        missing = Identifier.fromNamespaceAndPath(Constants.MODID, "firepit_block");
        fallback = Identifier.fromNamespaceAndPath(Constants.MODID, "firepit");
        BuiltInRegistries.BLOCK.addAlias(missing, fallback);
        missing = Identifier.fromNamespaceAndPath(Constants.MODID, "powder_bowl_block");
        fallback = Identifier.fromNamespaceAndPath(Constants.MODID, "powder_bowl");
        BuiltInRegistries.BLOCK.addAlias(missing, fallback);
    }
}
