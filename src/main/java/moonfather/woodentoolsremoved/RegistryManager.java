package moonfather.woodentoolsremoved;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import moonfather.woodentoolsremoved.items.BlockItemEx;
import moonfather.woodentoolsremoved.items.OptionalRecipeCondition;
import moonfather.woodentoolsremoved.items.firepit.FirepitBlock;
import moonfather.woodentoolsremoved.items.tools.FirestarterItem;
import moonfather.woodentoolsremoved.items.tools.HatchetItem;
import moonfather.woodentoolsremoved.items.javelin.JavelinItem;
import moonfather.woodentoolsremoved.items.tools.PickItem;
import moonfather.woodentoolsremoved.items.bowl.BowlBlock;
import moonfather.woodentoolsremoved.other.SticksAndFlintLootModifier;
import moonfather.woodentoolsremoved.items.javelin.ThrownJavelinProjectile;
import moonfather.woodentoolsremoved.original_tools.BonusChestLootModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RegistryManager
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Constants.MODID);

	public static void Init(IEventBus modBus)
	{
		RegistryManager.ITEMS.register(modBus);
		RegistryManager.BLOCKS.register(modBus);
		RegistryManager.LOOT_MODIFIERS.register(modBus);
		RegistryManager.ENTITIES.register(modBus);
		RegistryManager.CONDITIONS.register(modBus);
	}

	public static final Supplier<Item> ItemHatchet = ITEMS.register("flint_hatchet", () -> new HatchetItem());
	public static final Supplier<Item> ItemMiniPick = ITEMS.register("flint_pick", () -> new PickItem());
	public static final Supplier<Item> ItemPainting1 = ITEMS.register("painting1", () -> new Item(new Item.Properties()));
	public static final Supplier<Item> ItemJavelin = ITEMS.register("javelin", () -> new JavelinItem());
	public static final Supplier<Item> ItemFirestarter = ITEMS.register("firestarter", () -> new FirestarterItem());

	public static final Supplier<Block> BlockBlackPowderBowl = BLOCKS.register("powder_bowl_block", () -> new BowlBlock());
	public static final Supplier<Item> ItemBlackPowderBowl = ITEMS.register("powder_bowl", () -> BlockItemEx.Create(BlockBlackPowderBowl.get(), BowlBlock.GetItemProperties()).AppendTooltipLine(BowlBlock.TooltipLine1).AppendTooltipLine(BowlBlock.TooltipLine2));
	public static final Supplier<Block> BlockFirepit = BLOCKS.register("firepit_block", () -> new FirepitBlock());
	public static final Supplier<Item> ItemFirepit = ITEMS.register("firepit", () -> new BlockItem(BlockFirepit.get(), FirepitBlock.GetItemProperties()));

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIERS.register("loot_modifier_for_bonus_chest", BonusChestLootModifier.CODEC);
	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer2 = LOOT_MODIFIERS.register("loot_modifier_for_sticks_and_flint", SticksAndFlintLootModifier.CODEC);

	public static final Supplier<EntityType<ThrownJavelinProjectile>> ThrownJavelinProjectileET = ENTITIES.register("thrown_javelin_projectile", () -> EntityType.Builder.<ThrownJavelinProjectile>of(ThrownJavelinProjectile::new, net.minecraft.world.entity.MobCategory.MISC)
			.sized(ThrownJavelinProjectile.GetScale(), ThrownJavelinProjectile.GetScale() * 1.5f)
			.build("thrown_javelin_projectile"));

	public static final Supplier<MapCodec<? extends ICondition>> OptionalRecipe = CONDITIONS.register("optional", () -> OptionalRecipeCondition.CODEC);
}
