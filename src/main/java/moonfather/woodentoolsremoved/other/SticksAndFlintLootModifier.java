package moonfather.woodentoolsremoved.other;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.woodentoolsremoved.OptionsHolder;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SticksAndFlintLootModifier extends LootModifier
{
    private Item flintItem = null;
    private final Random random = new Random();
    public SticksAndFlintLootModifier(LootItemCondition[] conditionsIn, ResourceLocation item)
    {
        super(conditionsIn);
        this.itemToDrop = item;
        this.flintItem = BuiltInRegistries.ITEM.get(item);
        if (this.flintItem.equals(Items.AIR))
        {
            this.flintItem = Items.FLINT;
        }
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
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

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    private final ResourceLocation itemToDrop;

    public static final Supplier<Codec<SticksAndFlintLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.fieldOf("what_drops_from_gravel").forGetter((m) -> m.itemToDrop))
                    .apply(inst, SticksAndFlintLootModifier::new)));
}
