package moonfather.woodentoolsremoved.other;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementForPunchingLogs
{
    public static void Grant(Player player)
    {
        if (player instanceof ServerPlayer)
        {
            ServerPlayer sp = (ServerPlayer) player;
            sp.getAdvancements().award(GetAdvancement(sp), "impossible_bucket2");
        }
    }


    private static Advancement cachedInstance = null;

    private static Advancement GetAdvancement(ServerPlayer sp)
    {
        if (cachedInstance != null)
        {
            return cachedInstance;
        }

        cachedInstance = sp.getServer().getAdvancements().getAdvancement(Constants.Advancements.PUNCHER);
        return cachedInstance;
    }
}
