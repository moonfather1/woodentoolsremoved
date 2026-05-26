package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.Constants;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementForPunchingLogs
{
    public static void Grant(Player player)
    {
        if (player instanceof ServerPlayer sp)
        {
            AdvancementHolder ah = sp.level().getServer().getAdvancements().get(Constants.Advancements.PUNCHER);
            if (ah != null)
            {
                sp.getAdvancements().award(ah, "impossible_bucket2");
            }
        }
    }
}
