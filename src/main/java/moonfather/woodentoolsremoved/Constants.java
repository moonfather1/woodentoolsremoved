package moonfather.woodentoolsremoved;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Constants 
{ 
    public static final String MODID = "woodentoolsremoved";
    public static final int COLOR_GRAY_TOOLTIPS = 0x808080;
    public static final int COLOR_WARNING_TOOLTIPS = 0xd39015;
    public static final int COLOR_DUD_TOOLTIPS = 0x9e7b4d;
    public static final int COLOR_MESSAGE_GRAY = 0xC0C0C0;
    public static final double GAMEPLAY_FIRESTARTER_RANGE = 4.5;

    public static class Tags
    {
        public static final TagKey<Item> FLINT = ItemTags.create(new ResourceLocation("c:flint"));
        public static final TagKey<Item> IGNITES_GUNPOWDER = ItemTags.create(new ResourceLocation(MODID, "ignites_gunpowder"));
    }

    public static class Advancements
    {
        public static final ResourceLocation STONE1 = new ResourceLocation("woodentoolsremoved:tut/g4_get_stone1");
        public static final ResourceLocation STONE2 = new ResourceLocation("woodentoolsremoved:tut/g4_get_stone2");
        public static final ResourceLocation STONE3 = new ResourceLocation("woodentoolsremoved:tut/g4_get_stone3");
        public static final ResourceLocation PUNCHER = new ResourceLocation("woodentoolsremoved:tut/g2_puncher");
    }
}
