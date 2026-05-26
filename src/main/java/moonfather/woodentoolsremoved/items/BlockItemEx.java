package moonfather.woodentoolsremoved.items;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockItemEx extends BlockItem
{
    public static BlockItemEx create(Block block, Properties properties, ResourceKey<Item> id)
    {
        return new BlockItemEx(block, properties.setId(id));
    }

    private BlockItemEx(Block block, Item.Properties properties)
    {
        super(block, properties);
    }

    private Component line1 = null, line2 = null;
    private Supplier<Boolean> cond1 = null, cond2 = null;

    public BlockItemEx appendTooltipLine(Component line)
    {
        this.appendTooltipLine(()->true, line);
        return this;
    }

    public BlockItemEx appendTooltipLine(Supplier<Boolean> condition, Component line)
    {
        if (this.line1 == null)
        {
            this.line1 = line;
            this.cond1 = condition;
        }
        else if (this.line2 == null)
        {
            this.line2 = line;
            this.cond2 = condition;
        }
        return this;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        if (this.line1 != null && this.cond1.get())
        {
            builder.accept(this.line1);
            if (this.line2 != null && this.cond2.get())
            {
                builder.accept(this.line2);
            }
        }
    }
}
