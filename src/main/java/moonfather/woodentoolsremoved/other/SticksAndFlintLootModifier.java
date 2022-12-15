package moonfather.woodentoolsremoved.other;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;

import java.util.List;
import java.util.Random;

public class SticksAndFlintLootModifier extends LootModifier
{
    private Item flintItem = null;
    private Random random = new Random();
    public SticksAndFlintLootModifier(LootItemCondition[] conditionsIn, String item)
    {
        super(conditionsIn);
        this.flintItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
        if (this.flintItem == null || this.flintItem.equals(Items.AIR))
        {
            this.flintItem = Items.FLINT;
        }
    }

    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
    {
        if (context.getQueriedLootTableId().equals(Blocks.GRAVEL.getLootTable()))
        {
            Entity player = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (SticksAndFlintSupport.ShouldDropFlintForPlayer(player))
            {
                generatedLoot.add(this.flintItem.getDefaultInstance());
            }
            return generatedLoot;
        }

        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (state != null)
        {
            if (state.is(BlockTags.LEAVES))
            {
                if (this.random.nextInt(100) < OptionsHolder.COMMON.StickDropChance.get())
                {
                    generatedLoot.add(Items.STICK.getDefaultInstance());
                }
            }
        }
        return generatedLoot;
    }


    ///////////////////////////////////////////////////////////

    public static class Serializer extends GlobalLootModifierSerializer<SticksAndFlintLootModifier>
    {
        @Override
        public SticksAndFlintLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn)
        {
            String item;
            if (json != null && json.has("what_drops_from_gravel"))
            {
                item = json.get("what_drops_from_gravel").getAsString();
            }
            else
            {
                item = "minecraft:flint";
            }
            return new SticksAndFlintLootModifier(conditionsIn, item);
        }

        @Override
        public JsonObject write(SticksAndFlintLootModifier dropsModifier)
        {
            JsonObject result = new JsonObject();
            result.add("conditions", new JsonArray());
            result.addProperty("what_drops_from_gravel", dropsModifier.flintItem.getRegistryName().toString());
            result.addProperty("a_hint", "You can replace this file in datapack to set what drops from gravel, if your mod adds small flint pieces for example.");
            return result;
        }
    }
}
