package moonfather.woodentoolsremoved.peaceful;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class PeacefulTooltips
{

/////////  hatchet  /////////////
//   if (ClientLevelAccessor.isPeaceful() && PeacefulGameplaySupport.HaveCoalDust())
//    {
//        lines.add(TooltipForPeacefulLine1);
//        lines.add(TooltipForPeacefulLine2);
//    }

    private static final Component TooltipForPeacefulLine1 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip1").withStyle(Style.EMPTY.withColor(0xd0a949));
    private static final Component TooltipForPeacefulLine2 = Component.translatable("item.woodentoolsremoved.peaceful.tooltip2").withStyle(Style.EMPTY.withColor(0xd0a949));

}
