package moonfather.woodentoolsremoved;

import moonfather.woodentoolsremoved.items.BlockItemEx;
import moonfather.woodentoolsremoved.items.firepit.FirepitBlock;
import moonfather.woodentoolsremoved.items.tools.HatchetItem;
import moonfather.woodentoolsremoved.items.javelin.JavelinItem;
import moonfather.woodentoolsremoved.items.tools.PickItem;
import moonfather.woodentoolsremoved.items.bowl.BowlBlock;
import moonfather.woodentoolsremoved.other.SticksAndFlintLootModifier;
import moonfather.woodentoolsremoved.items.javelin.ThrownJavelinProjectile;
import moonfather.woodentoolsremoved.original_tools.BonusChestLootModifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryManager
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Constants.MODID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Constants.MODID);

	public static void Init()
	{
		RegistryManager.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RegistryManager.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RegistryManager.LOOT_MODIFIER_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RegistryManager.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<Item> ItemHatchet = ITEMS.register("flint_hatchet", () -> new HatchetItem());
	public static final RegistryObject<Item> ItemMiniPick = ITEMS.register("flint_pick", () -> new PickItem());
	public static final RegistryObject<Item> ItemPainting1 = ITEMS.register("painting1", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ItemJavelin = ITEMS.register("javelin", () -> new JavelinItem());

	public static final RegistryObject<Block> BlockBlackPowderBowl = BLOCKS.register("powder_bowl_block", () -> new BowlBlock());
	public static final RegistryObject<Item> ItemBlackPowderBowl = ITEMS.register("powder_bowl", () -> BlockItemEx.Create(BlockBlackPowderBowl.get(), BowlBlock.GetItemProperties()).AppendTooltipLine(BowlBlock.TooltipLine1).AppendTooltipLine(BowlBlock.TooltipLine2));
	public static final RegistryObject<Block> BlockFirepit = BLOCKS.register("firepit_block", () -> new FirepitBlock());
	public static final RegistryObject<Item> ItemFirepit = ITEMS.register("firepit", () -> new BlockItem(BlockFirepit.get(), FirepitBlock.GetItemProperties()));

	public static final RegistryObject<GlobalLootModifierSerializer<BonusChestLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIER_SERIALIZERS.register("loot_modifier_for_bonus_chest", BonusChestLootModifier.Serializer::new);
	public static final RegistryObject<GlobalLootModifierSerializer<SticksAndFlintLootModifier>> StupidGLMSerializer2 = LOOT_MODIFIER_SERIALIZERS.register("loot_modifier_for_sticks_and_flint", SticksAndFlintLootModifier.Serializer::new);

	public static final RegistryObject<EntityType<ThrownJavelinProjectile>> ThrownJavelinProjectileET = ENTITIES.register("thrown_javelin_projectile", () -> EntityType.Builder.<ThrownJavelinProjectile>of(ThrownJavelinProjectile::new, MobCategory.MISC)
			.setCustomClientFactory(ThrownJavelinProjectile::new)
			.sized(ThrownJavelinProjectile.GetScale(), ThrownJavelinProjectile.GetScale())
			.build("thrown_javelin_projectile"));
}
