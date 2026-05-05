package moonfather.woodentoolsremoved.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class BlockItemEx extends BlockItem
{
    public static BlockItemEx create(Block block, Item.Properties properties)
    {
        return new BlockItemEx(block, properties);
    }

    private BlockItemEx(Block block, Item.Properties properties)
    {
        super(block, properties);
    }

    private Component line1 = null, line2 = null;
    public BlockItemEx AppendTooltipLine(Component line)
    {
        if (this.line1 == null)
        {
            this.line1 = line;
        }
        else if (this.line2 == null)
        {
            this.line2 = line;
        }
        return this;
    }

    public BlockItemEx AppendTooltipLine(boolean condition, Component line)
    {
        if (condition)
        {
            this.AppendTooltipLine(line);
        }
        return this;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        if (line1 != null)
        {
            builder.accept(line1);
            if (line2 != null)
            {
                builder.accept(line2);
            }
        }
    }
}
