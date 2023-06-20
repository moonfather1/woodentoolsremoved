package moonfather.woodentoolsremoved.peaceful;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
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
        if (HaveCoalDust() && event.getState().is(BlockTags.COAL_ORES) && event.getEntity().getMainHandItem().is(TagAxe) && event.getEntity().level().getDifficulty().equals(Difficulty.PEACEFUL))
        {
            if (event.getPosition().isPresent()) {
                BlockPos pos = event.getPosition().get();
                if (!event.getEntity().level().isClientSide && event.getEntity().level().random.nextInt(100) < 5) {
                    event.getEntity().level().playSound((Player) null, pos, SoundEvents.BASALT_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    event.getEntity().level().setBlockAndUpdate(pos, GetBaseBlock(event.getState()).defaultBlockState());
                    Block.popResource(event.getEntity().level(), pos, new ItemStack(coalDust));
                    ItemStack stack = event.getEntity().getMainHandItem();
                    int damage = Math.max(4, (stack.getMaxDamage() - stack.getDamageValue()) / 2);
                    stack.hurtAndBreak(damage, event.getEntity(), (p) -> {
                    });
                }
            }
            return;
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
        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(tag))
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
