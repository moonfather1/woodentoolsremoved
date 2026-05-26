package moonfather.woodentoolsremoved;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class Constants 
{ 
    public static final String MODID = "woodentoolsremoved";
    public static final int COLOR_GRAY_TOOLTIPS = 0x808080;
    public static final int COLOR_WARNING_TOOLTIPS = 0xc58515;
    public static final int COLOR_DUD_TOOLTIPS = 0x9e7b4d;
    public static final int COLOR_MESSAGE_GRAY = 0xC0C0C0;
    public static final double GAMEPLAY_FIRESTARTER_RANGE = 4.5;

    public static class Materials
    {
        public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 12, 4.0F, 1.0F, 5, Tags.FLINT);
    }

    public static class Tags
    {
        public static final TagKey<Item> FLINT = ItemTags.create(Identifier.parse("c:flint"));
        public static final TagKey<Item> IGNITES_GUNPOWDER = ItemTags.create(Identifier.fromNamespaceAndPath(MODID, "ignites_gunpowder"));
    }

    public static class Advancements
    {
        public static final Identifier STONE1 = Identifier.fromNamespaceAndPath(MODID, "tut/g4_get_stone1");
        public static final Identifier STONE2 = Identifier.fromNamespaceAndPath(MODID, "tut/g4_get_stone2");
        public static final Identifier STONE3 = Identifier.fromNamespaceAndPath(MODID, "tut/g4_get_stone3");
        public static final Identifier PUNCHER = Identifier.fromNamespaceAndPath(MODID, "tut/g2_puncher");
    }
}
