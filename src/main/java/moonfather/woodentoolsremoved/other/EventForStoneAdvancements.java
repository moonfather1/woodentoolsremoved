package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForStoneAdvancements
{
    @SubscribeEvent
    public static void OnAdvancement(AdvancementEvent event)
    {
        if (event.getAdvancement().getId().toString().equals("woodentoolsremoved:tut/g2_hatchet"))
        {
            if (event.getPlayer() instanceof ServerPlayer)
            {
                ServerPlayer sp = (ServerPlayer) event.getPlayer();
                if (OptionsHolder.IsResolvedModeSimple() && ! ModList.get().isLoaded("tconstruct"))
                {
                    GetAdvancement(sp, Constants.Advancements.STONE2).getDisplay().hidden = false;
                }
                else if (OptionsHolder.IsResolvedModeHard() && (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get()))
                {
                    GetAdvancement(sp, Constants.Advancements.STONE1).getDisplay().hidden = false;
                }
                else
                {
                    sp.getAdvancements().award(GetAdvancement(sp, Constants.Advancements.STONE3), "impossible_bucket");
                }
            }
        }
    }


    private static Advancement GetAdvancement(ServerPlayer sp, ResourceLocation id)
    {
        return sp.getServer().getAdvancements().getAdvancement(id);
    }


    /*
    private static void RemoveAdvancement(ServerPlayer sp, String[] names)
    {
        Set<ResourceLocation> s = new HashSet<ResourceLocation>();
        for (String name : names)
        {
            s.add(new ResourceLocation("woodentoolsremoved:tut/" + name));
        }
        sp.getServer().getAdvancements().advancements.remove(s);
    }
    private static void RemoveAdvancement(ServerPlayer sp, String name)
    {
        Set<ResourceLocation> s = new HashSet<ResourceLocation>();
        s.add(new ResourceLocation("woodentoolsremoved:tut/" + name));
        sp.getServer().getAdvancements().advancements.remove(s);
    }

     */
}
