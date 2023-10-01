package moonfather.woodentoolsremoved.original_tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;

public class BonusChestLootModifier extends LootModifier
{
    public BonusChestLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
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





    public static class Serializer extends GlobalLootModifierSerializer<BonusChestLootModifier>
    {
        @Override
        public BonusChestLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn)
        {
            return new BonusChestLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(BonusChestLootModifier luckBlockDropsModifier)
        {
            JsonObject result = new JsonObject();
            result.add("conditions", new JsonArray());
            return result;
        }
    }
}
