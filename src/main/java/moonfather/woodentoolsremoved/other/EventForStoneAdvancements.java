package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

@EventBusSubscriber
public class EventForStoneAdvancements
{
    @SubscribeEvent
    public static void OnAdvancement(AdvancementEvent.AdvancementEarnEvent event)
    {
        if (event.getAdvancement().id().toString().equals("woodentoolsremoved:tut/g2_hatchet"))
        {
            if (event.getEntity() instanceof ServerPlayer)
            {
                ServerPlayer sp = (ServerPlayer) event.getEntity();
                if (OptionsHolder.IsResolvedModeSimple() && ! ModList.get().isLoaded("tconstruct"))
                {
                    AdvancementHolder a = GetAdvancement(sp, Constants.Advancements.STONE2);
                    a.value().display().ifPresent(displayInfo -> displayInfo.hidden = false);
                }
                else if (OptionsHolder.IsResolvedModeHard() && (! ModList.get().isLoaded("tconstruct") || OptionsHolder.COMMON.ForceHardModeWithTC.get()))
                {
                    AdvancementHolder a = GetAdvancement(sp, Constants.Advancements.STONE1);
                    a.value().display().ifPresent(displayInfo -> displayInfo.hidden = false);
                }
                else
                {
                    sp.getAdvancements().award(GetAdvancement(sp, Constants.Advancements.STONE3), "impossible_bucket");
                }
            }
        }
    }


    private static AdvancementHolder GetAdvancement(ServerPlayer sp, ResourceLocation id)
    {
        return sp.getServer().getAdvancements().get(id);
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
