package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForCampfires
{
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event)
    {
        if (event.getState().is(BlockTags.CAMPFIRES))
        {
            if (! event.getBlockSnapshot().getReplacedBlock().is(BlockTags.CAMPFIRES)) // new
            {
                if (OptionsHolder.COMMON.CampfiresStartUnlit.get())
                {
                    // event.setCanceled(true);  //can't. broken.  https://github.com/neoforged/NeoForge/issues/382
                    event.getWorld().setBlock(event.getPos(), event.getState().setValue(CampfireBlock.LIT, false), 3);
                }
            }
        }
    }
}