package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.CampfireBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber
public class EventForCampfires
{
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event)
    {
        if (event.getState().is(BlockTags.CAMPFIRES))
        {
            if (! event.getBlockSnapshot().getState().is(BlockTags.CAMPFIRES)) // new
            {
                if (OptionsHolder.COMMON.CampfiresStartUnlit.get())
                {
                    // event.setCanceled(true);  //can't. broken.  https://github.com/neoforged/NeoForge/issues/382
                    event.getLevel().setBlock(event.getPos(), event.getState().setValue(CampfireBlock.LIT, false), 3);
                }
            }
        }
    }
}
