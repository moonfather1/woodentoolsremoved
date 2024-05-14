package moonfather.woodentoolsremoved.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class FlintToolTier implements Tier
{
    private static final FlintToolTier instance = new FlintToolTier();

    public static FlintToolTier getInstance() { return instance; }

    ///////////////////////////////////////

    @Override
    public int getUses()
    {
        return 12;
    }

    @Override
    public float getSpeed()
    {
        return 2.0f;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return 1.0f;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops()
    {
        return BlockTags.INCORRECT_FOR_STONE_TOOL;
    }

    @Override
    public int getEnchantmentValue()
    {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.EMPTY;
    }
}
