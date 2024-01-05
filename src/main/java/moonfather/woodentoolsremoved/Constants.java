package moonfather.woodentoolsremoved;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Constants
{
    public static final String MODID = "woodentoolsremoved";

    public static class Tags
    {
        public static final TagKey<Item> FLINT = ItemTags.create(new ResourceLocation("forge:flint"));
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
