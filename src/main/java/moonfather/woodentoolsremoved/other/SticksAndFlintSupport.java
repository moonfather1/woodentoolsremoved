package moonfather.woodentoolsremoved.other;

import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class SticksAndFlintSupport
{
    public static boolean ShouldDropFlintForPlayer(Entity player)
    {
        if (! OptionsHolder.COMMON.GuaranteedFlintDrops.get())
        {
            return false;
        }
        if (!(player instanceof Player))
        {
            return false;
        }
        CompoundTag tag = player.getPersistentData();
        int blocks = tag.getInt("gravel_blocks_broken");
        blocks += 1;
        if (blocks > 20)
        {
            return false;
        }
        tag.putInt("gravel_blocks_broken", blocks);
        return blocks == 2 || blocks == 6 || blocks == 10;
    }
}
