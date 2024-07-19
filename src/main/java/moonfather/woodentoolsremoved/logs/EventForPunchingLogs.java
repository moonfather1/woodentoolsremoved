package moonfather.woodentoolsremoved.logs;

import com.mojang.logging.LogUtils;
import moonfather.woodentoolsremoved.other.AdvancementForPunchingLogs;
import moonfather.woodentoolsremoved.other.TetraSupport;
import moonfather.woodentoolsremoved.peaceful.PeacefulGameplaySupport;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Mod.EventBusSubscriber
public class EventForPunchingLogs
{
	private static final Component woodenToolMessage = Component.translatable("item.woodentoolsremoved.original.tooltip2").withStyle(Style.EMPTY.withColor(0x9e7b4d));
    private static final Component tetraWoodenToolMessage = Component.translatable("item.woodentoolsremoved.tetra.tooltip").withStyle(Style.EMPTY.withColor(0x9e7b4d));

	private static final Component[] handHurtsMessages = new Component[]
			{
				Component.translatable("message.woodentoolsremoved.tree_message_h1").withStyle(Style.EMPTY.withColor(0xf46a4e)),
				Component.translatable("message.woodentoolsremoved.tree_message_h2").withStyle(Style.EMPTY.withColor(0xd86a54))
			};

	private static final Component[] handNoEffectMessages = new Component[]
			{
				Component.translatable("message.woodentoolsremoved.tree_message_n1").withStyle(Style.EMPTY.withColor(0xf4b74e)),
				Component.translatable("message.woodentoolsremoved.tree_message_n2").withStyle(Style.EMPTY.withColor(0xf4b74e)),
				Component.translatable("message.woodentoolsremoved.tree_message_n3").withStyle(Style.EMPTY.withColor(0xe8b45a)),
				Component.translatable("message.woodentoolsremoved.tree_message_n4").withStyle(Style.EMPTY.withColor(0xe8b45a)),
				Component.translatable("message.woodentoolsremoved.tree_message_n5").withStyle(Style.EMPTY.withColor(0xf4b74e))
			};

    private static boolean usingTetra = false, checkedForTetra = false;



	@SubscribeEvent
	public static void OnBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		if ( ! event.getEntity().getMainHandItem().isEmpty())
		{
			ResourceLocation toolId = ForgeRegistries.ITEMS.getKey(event.getEntity().getMainHandItem().getItem());
			if ( event.getEntity().getMainHandItem().getItem() instanceof AxeItem && (((AxeItem)event.getEntity().getMainHandItem().getItem()).getTier().equals(Tiers.WOOD) && (toolId == null || ! toolId.getNamespace().equals("silentgear")))
					|| event.getEntity().getMainHandItem().getItem() instanceof PickaxeItem pick && ! event.getEntity().getMainHandItem().isCorrectToolForDrops(Blocks.IRON_ORE.defaultBlockState()) && ! pick.getTier().equals(Tiers.GOLD)
					|| toolId.toString().equals("tconstruct:pickaxe") && event.getEntity().getMainHandItem().getTag().getCompound("tic_stats").getString("tconstruct:harvest_tier").equals("minecraft:wood")
			)
			{
				if (ShouldShowMessage(event.getEntity()))
				{
					event.getEntity().displayClientMessage(woodenToolMessage, true);
				}
				event.setCanceled(true);
				return;
			}
            if (! checkedForTetra)
            {
                usingTetra = ModList.get().isLoaded("tetra");
                checkedForTetra = true;
            }

            if (usingTetra && ForgeRegistries.ITEMS.getKey(event.getEntity().getMainHandItem().getItem()).toString().equals(TetraSupport.DoubleToolId))
            {
				if (TetraSupport.IsWoodenTetraTool(event.getEntity().getMainHandItem()))
				{
					if (ShouldShowMessage(event.getEntity())) {
						event.getEntity().displayClientMessage(tetraWoodenToolMessage, true);
					}
					event.setCanceled(true);
					return;
				}
            }
		}

		if (event.getState().is(BlockTags.LOGS) && ! event.getEntity().getMainHandItem().isCorrectToolForDrops(event.getState()))
		{
			// event.getPlayer().level.isClientSide()     alternates
			event.setCanceled(true);
			if (event.getEntity().getArmorCoverPercentage() > 0f)
			{
				return; // later game, accidental left-click
			}
			if (! event.getEntity().getMainHandItem().isEmpty() && event.getEntity().getMainHandItem().is(Tags.Items.TOOLS))
			{
				event.setNewSpeed(event.getOriginalSpeed() / 8);
				event.setCanceled(false);
				return;
			}
			if (! event.getEntity().level().isClientSide() && ShouldGiveAdvancement(event.getEntity()))
			{
				if (ModList.get().isLoaded("multimine") && (event.getPosition().isEmpty() || ShouldAbortMultiMine(event.getEntity(), event.getPosition().get())))
				{
					return; // this mod keeps asking about break speed after we stop hitting the block.
				}
				AdvancementForPunchingLogs.Grant(event.getEntity());
			}
			if (! event.getEntity().level().isClientSide() && ShouldShowMessage(event.getEntity()))
			{
				if (ModList.get().isLoaded("multimine") && (event.getPosition().isEmpty() || ShouldAbortMultiMine(event.getEntity(), event.getPosition().get())))
				{
					return; // this mod keeps asking about break speed after we stop hitting the block.
				}
				if (ShouldHurtPlayer(event.getEntity()))
				{
					event.getEntity().hurt(event.getEntity().damageSources().flyIntoWall(), 1);
					int m = event.getEntity().level().getRandom().nextInt(handHurtsMessages.length);
					event.getEntity().displayClientMessage(handHurtsMessages[m], true);
				}
				else
				{
					int m = event.getEntity().level().getRandom().nextInt(handNoEffectMessages.length);
					event.getEntity().displayClientMessage(handNoEffectMessages[m], true);
				}
			}
		}

		PeacefulGameplaySupport.CheckForHittingCoalOre(event);
	}





	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Map<UUID, Long> lastClientMessageTick = new HashMap<>();
	private static final Map<UUID, Long> prevClientMessageTick = new HashMap<>();
	private static final Map<UUID, Long> lastHurtPlayerTick = new HashMap<>();

	private static boolean ShouldShowMessage(Player player)
	{
		Long last = lastClientMessageTick.get(player.getUUID());
		if (last == null)
		{
			prevClientMessageTick.put(player.getUUID(), player.level().getGameTime() - 200);
			lastClientMessageTick.put(player.getUUID(), player.level().getGameTime());
			return true;
		}
		if (player.level().getGameTime() - last.longValue() > 3*20)
		{
			prevClientMessageTick.put(player.getUUID(), last);
			lastClientMessageTick.put(player.getUUID(), player.level().getGameTime());
			return true;
		}
		else
		{
			return false;
		}
	}


	private static boolean ShouldHurtPlayer(Player player)
	{
		Long last = lastHurtPlayerTick.get(player.getUUID());
		if (last == null)
		{
			lastHurtPlayerTick.put(player.getUUID(), player.level().getGameTime() + 80);
			return false;
		}
		boolean longTimeSinceLastPunch = player.level().getGameTime() - prevClientMessageTick.get(player.getUUID()) > 5*20;  // so we don't hurt instantly after every pause in punching
		if (player.level().getGameTime() - last.longValue() > 7*20 && !longTimeSinceLastPunch)
		{
			lastHurtPlayerTick.put(player.getUUID(), player.level().getGameTime());
			return true;
		}
		else
		{
			return false;
		}
	}


	private static boolean ShouldGiveAdvancement(Player player)
	{
		Long last = lastClientMessageTick.get(player.getUUID());
		if (last == null)
		{
			return false;
		}
		Long lastHurt = lastHurtPlayerTick.get(player.getUUID());
		if (player.level().getGameTime() - last > 3*20 /*time for msg*/ && (lastHurt != null && player.level().getGameTime() - lastHurt > 1.5*20/*recently hurt*/))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//-----------------------------------------------------------

	private static boolean ShouldAbortMultiMine(Player player, BlockPos clickedBlock)
	{
		BlockHitResult blockhitresult = getPlayerPOVHitResult(player.level(), player);
		return blockhitresult.getType() == HitResult.Type.MISS ||
				blockhitresult.getType() == HitResult.Type.BLOCK &&
						(blockhitresult.distanceTo(player) > 5.7 || ! blockhitresult.getBlockPos().equals(clickedBlock));
	}

	private static BlockHitResult getPlayerPOVHitResult(Level level, Player player)
	{
		float f = player.getXRot();
		float f1 = player.getYRot();
		Vec3 vec3 = player.getEyePosition();
		float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
		float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
		float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = 6; // reach
		Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
		return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
	}
}
