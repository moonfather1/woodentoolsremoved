package moonfather.woodentoolsremoved;

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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class RegistryManager
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Constants.MODID);

	public static void init(IEventBus modBus)
	{
		RegistryManager.ITEMS.register(modBus);
		RegistryManager.BLOCKS.register(modBus);
		RegistryManager.LOOT_MODIFIERS.register(modBus);
		RegistryManager.ENTITIES.register(modBus);
		RegistryManager.CONDITIONS.register(modBus);
	}

	private static final Identifier id1 = Identifier.fromNamespaceAndPath(Constants.MODID, "flint_hatchet");
	private static final Identifier id2 = Identifier.fromNamespaceAndPath(Constants.MODID, "flint_pick");
	private static final Identifier id3 = Identifier.fromNamespaceAndPath(Constants.MODID, "javelin");
	private static final Identifier id4 = Identifier.fromNamespaceAndPath(Constants.MODID, "firestarter");
	private static final Identifier id5 = Identifier.fromNamespaceAndPath(Constants.MODID, "barrier1");
	private static final Identifier id6 = Identifier.fromNamespaceAndPath(Constants.MODID, "painting1");
	private static final ResourceKey<Item> key1 = ResourceKey.create(Registries.ITEM, id1);
	private static final ResourceKey<Item> key2 = ResourceKey.create(Registries.ITEM, id2);
	private static final ResourceKey<Item> key3 = ResourceKey.create(Registries.ITEM, id3);
	private static final ResourceKey<Item> key4 = ResourceKey.create(Registries.ITEM, id4);
	private static final ResourceKey<Item> key5 = ResourceKey.create(Registries.ITEM, id5);
	private static final ResourceKey<Item> key6 = ResourceKey.create(Registries.ITEM, id6);
	public static final Supplier<Item> ItemHatchet = ITEMS.register(id1.getPath(), () -> new HatchetItem(key1));
	public static final Supplier<Item> ItemMiniPick = ITEMS.register(id2.getPath(), () -> new PickItem(key2));
	public static final Supplier<Item> ItemPainting1 = ITEMS.register(id6.getPath(), () -> new Item(new Item.Properties().setId(key6)));
	public static final Supplier<Item> ItemBarrier1 = ITEMS.register(id5.getPath(), () -> new Item(new Item.Properties().setId(key5).component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("item.woodentoolsremoved.barrier1.tooltip1"))))));
	public static final Supplier<Item> ItemJavelin = ITEMS.register(id3.getPath(), () -> new JavelinItem(key3));
	public static final Supplier<Item> ItemFirestarter = ITEMS.register(id4.getPath(), () -> new FirestarterItem(key4));

	private static final Identifier id7 = Identifier.fromNamespaceAndPath(Constants.MODID, "powder_bowl");
	private static final Identifier id8 = Identifier.fromNamespaceAndPath(Constants.MODID, "firepit");
	public static final Supplier<Block> BlockBlackPowderBowl = BLOCKS.register(id7.getPath(), () -> new BowlBlock(ResourceKey.create(Registries.BLOCK, id7)));
	public static final Supplier<Item> ItemBlackPowderBowl = ITEMS.register(id7.getPath(), () -> BlockItemEx.create(BlockBlackPowderBowl.get(), BowlBlock.getItemProperties(), ResourceKey.create(Registries.ITEM, id7)).appendTooltipLine(BowlBlock.TooltipLine1).appendTooltipLine(BowlBlock.TooltipLine2));
	public static final Supplier<Block> BlockFirepit = BLOCKS.register(id8.getPath(), () -> new FirepitBlock(ResourceKey.create(Registries.BLOCK, id8)));
	public static final Supplier<Item> ItemFirepit = ITEMS.register(id8.getPath(), () -> BlockItemEx.create(BlockFirepit.get(), FirepitBlock.getItemProperties(), ResourceKey.create(Registries.ITEM, id8)).appendTooltipLine(OptionsHolder.COMMON.EnableFirestarter, FirepitBlock.TooltipForFirepitLine1).appendTooltipLine(OptionsHolder.COMMON.EnableFirestarter, FirepitBlock.TooltipForFirepitLine2));

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIERS.register("loot_modifier_for_bonus_chest", BonusChestLootModifier.CODEC);
	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer2 = LOOT_MODIFIERS.register("loot_modifier_for_sticks_and_flint", SticksAndFlintLootModifier.CODEC);

	public static final Supplier<EntityType<ThrownJavelinProjectile>> ThrownJavelinProjectileET = ENTITIES.register("thrown_javelin_projectile", () -> EntityType.Builder.<ThrownJavelinProjectile>of(ThrownJavelinProjectile::new, net.minecraft.world.entity.MobCategory.MISC)
			.sized(ThrownJavelinProjectile.GetScale(), ThrownJavelinProjectile.GetScale() * 1.5f)
			.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Constants.MODID, "thrown_javelin_projectile"))));

	public static final Supplier<MapCodec<? extends ICondition>> OptionalRecipe = CONDITIONS.register("optional", () -> OptionalRecipeCondition.CODEC);
}
