package moonfather.woodentoolsremoved.original_tools;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import java.util.function.Supplier;

public class BonusChestLootModifier extends LootModifier
{
    public BonusChestLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if (!context.getQueriedLootTableId().equals(BuiltInLootTables.SPAWN_BONUS_CHEST))
        {
            return generatedLoot;
        }

        for (int i = 0; i < generatedLoot.size(); i++)
        {
            if (generatedLoot.get(i).getItem() instanceof AxeItem)
            {
                generatedLoot.set(i, new ItemStack(RegistryManager.ItemHatchet.get()));
            }
            else if (generatedLoot.get(i).getItem() instanceof PickaxeItem)
            {
                generatedLoot.set(i, new ItemStack(Items.FLINT, 3));
            }
            else if (generatedLoot.get(i).is(ItemTags.LOGS))
            {
                int roll = context.getLevel().random.nextInt(100);
                if (roll < 30)
                {
                    generatedLoot.set(i, new ItemStack(Items.FLINT, 1 + roll % 2));
                }
            }
        }
        return generatedLoot;
    }



    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    public static final Supplier<Codec<BonusChestLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .apply(inst, BonusChestLootModifier::new)));
}
