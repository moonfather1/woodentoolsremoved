package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
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
            if (event.getEntity() instanceof ServerPlayer)
            {
                ServerPlayer sp = (ServerPlayer) event.getEntity();
                if (OptionsHolder.IsResolvedModeSimple() && ! ModList.get().isLoaded("tconstruct"))
                {
                    DisplayInfo di = GetAdvancement(sp, "g4_get_stone2").getDisplay();
                    di.hidden = false;
                }
                else if (OptionsHolder.IsResolvedModeHard() && (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get()))
                {
                    DisplayInfo di = GetAdvancement(sp, "g4_get_stone1").getDisplay();
                    di.hidden = false;
                }
                else
                {
                    sp.getAdvancements().award(GetAdvancement(sp, "g4_get_stone3"), "impossible_bucket");
                }
            }
        }
    }


    private static Advancement GetAdvancement(ServerPlayer sp, String name)
    {
        return sp.getServer().getAdvancements().getAdvancement(new ResourceLocation("woodentoolsremoved:tut/" + name));
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
