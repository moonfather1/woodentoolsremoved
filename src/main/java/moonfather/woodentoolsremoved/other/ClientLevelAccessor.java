package moonfather.woodentoolsremoved.other;

import net.minecraft.client.Minecraft;
import net.minecraft.world.Difficulty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientLevelAccessor
{
    public static boolean isPeaceful()
    {
        return Minecraft.getInstance().level != null && Minecraft.getInstance().level.getDifficulty().equals(Difficulty.PEACEFUL);
    }
}
