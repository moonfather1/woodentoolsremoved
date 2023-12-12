package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementForPunchingLogs
{
    public static void Grant(Player player)
    {
        if (player instanceof ServerPlayer)
        {
            ServerPlayer sp = (ServerPlayer) player;
            sp.getAdvancements().award(sp.getServer().getAdvancements().get(Constants.Advancements.PUNCHER), "impossible_bucket2");
        }
    }
}
