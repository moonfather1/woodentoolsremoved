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
} 
