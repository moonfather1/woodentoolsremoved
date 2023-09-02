package moonfather.woodentoolsremoved.peaceful;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PeacefulGameplaySupport
{
    public static void CheckForHittingCoalOre(PlayerEvent.BreakSpeed event)
    {
        if (! checkedForCoalCust)
        {
            coalDust = GetFirstItemMatchingTag(TagCoalDust);
            checkedForCoalCust = true;
        }
        if (coalDust != null && event.getState().is(BlockTags.COAL_ORES) && event.getEntity().getMainHandItem().is(TagAxe) && event.getEntity().getLevel().getDifficulty().equals(Difficulty.PEACEFUL))
        {
            if (event.getEntity().getLevel().random.nextInt(100) < 4)
            {
                if (event.getEntity().getLevel().isClientSide())
                {
                    event.getEntity().getLevel().playSound((Player)null, event.getPos(), SoundEvents.BASALT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                if (! event.getEntity().getLevel().isClientSide())
                {
                    event.getEntity().getLevel().setBlockAndUpdate(event.getPos(), GetBaseBlock(event.getState()).defaultBlockState());
                    Block.popResource(event.getEntity().getLevel(), event.getPos(), new ItemStack(coalDust));
                    ItemStack stack = event.getEntity().getMainHandItem();
                    int damage = Math.max(4, (stack.getMaxDamage() - stack.getDamageValue()) / 2);
                    stack.hurtAndBreak(damage, event.getEntity(), (p) -> {});
                }
            }
        }
    }

    public static boolean HaveCoalDust()
    {
        if (! checkedForCoalCust)
        {
            coalDust = GetFirstItemMatchingTag(TagCoalDust);
            checkedForCoalCust = true;
        }
        return coalDust != null;
    }

    private static Holder<Item> GetFirstItemMatchingTag(TagKey<Item> tag)
    {
        for (Holder<Item> holder : Registry.ITEM.getTagOrEmpty(tag))
        {
            return holder;
        }
        return null;
    }


    private static Block GetBaseBlock(BlockState stateOfOreBlock)
    {
        if (ForgeRegistries.BLOCKS.getKey(stateOfOreBlock.getBlock()).toString().contains("slate"))
        {
            return Blocks.DEEPSLATE;
        }
        else
        {
            return Blocks.STONE;
        }
    }

    private static boolean checkedForCoalCust = false; private static Holder<Item> coalDust = null;

    private static final TagKey<Item> TagCoalDust = ItemTags.create(new ResourceLocation("forge:dusts/coal"));
    private static final TagKey<Item> TagAxe = ItemTags.create(new ResourceLocation("forge:tools/axes"));
}
