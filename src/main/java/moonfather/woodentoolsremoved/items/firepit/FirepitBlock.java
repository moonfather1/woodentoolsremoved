package moonfather.woodentoolsremoved.items.firepit;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.OptionsHolder;
import moonfather.woodentoolsremoved.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.function.ToIntFunction;

public class FirepitBlock extends CampfireBlock
{
    public FirepitBlock(ResourceKey<Block> id)
    {
        super(false /*spawnParticles*/, 0 /*fireDamage*/, BlockBehaviour.Properties.of().strength(1.0F).sound(SoundType.GRAVEL).lightLevel(litBlockEmission(7)).noOcclusion().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).setId(id));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue)
    {
        return (state) ->
        {
            return state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
        };
    }



    public static Item.Properties getItemProperties()
    {
        return new Item.Properties();
    }


    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @org.jspecify.annotations.Nullable Orientation orientation, boolean movedByPiston)
    {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
        // used to be if (pos2.getY() == pos.getY() - 1)
        // can't check that as of 26.1.   we don't know which neighbor block has changed anymore,
        BlockState below = level.getBlockState(pos.below());
        if (! level.isClientSide() && ! below.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER))
        {
            level.destroyBlock(pos, true);
        }
        // if pos2.getY() == pos.getY())
        for (Direction d : Direction.values())
        {
            BlockPos pos2 = pos.relative(d);
            FluidState newBlock = level.getFluidState(pos2);
            if (! level.isClientSide() && ! newBlock.is(Fluids.EMPTY))
            {
                level.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        if (pos.getY() <= level.getMinY())
        {
            return false;
        }
        BlockPos belowPos = pos.below();
        BlockState below = level.getBlockState(belowPos);
        if (! below.isFaceSturdy(level, belowPos, Direction.UP, SupportType.CENTER))
        {
            return false;
        }
        return super.canSurvive(blockState, level, pos);
    }


    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player)
    {
        return RegistryManager.ItemFirepit.get().getDefaultInstance();
    }



    private static final VoxelShape SHAPE_BEAM = Block.box(2d, 0.0D, 2d, 14d, 11d, 14d);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return SHAPE_BEAM;
    }

    public static final Component TooltipForFirepitLine1 = Component.translatable("item.woodentoolsremoved.firepit.tooltip1").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));
    public static final Component TooltipForFirepitLine2 = Component.translatable("item.woodentoolsremoved.firepit.tooltip2").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));

    ///////////////////////////////////////////***********************

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult)
    {
        if (level.getBlockEntity(blockPos) instanceof CampfireBlockEntity campfireblockentity)
        {
            if (player.isCrouching())
            {
                if (blockState.getValue(CampfireBlock.LIT))
                {
                    if (! level.isClientSide())
                    {
                        level.playSound((Player)null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    dowse((Entity)null, level, blockPos, blockState);
                    level.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(false)), 3);
                }
                else if (! OptionsHolder.COMMON.EnableFirestarter.get())
                {
                    // can light with empty hand
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                    level.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(true)), 3);
                }
                else
                {
                    // need firestarter
                    player.sendOverlayMessage(ERROR_NEED_TOOL);
                }
                return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
    private static final Component ERROR_NEED_TOOL = Component.translatable("item.woodentoolsremoved.firepit.error").withColor(Constants.COLOR_MESSAGE_GRAY);



    @Override
    protected InteractionResult useItemOn( ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult )
    {
        if (level.getBlockEntity(pos) instanceof CampfireBlockEntity campfire)
        {
            ItemStack itemInHand = player.getItemInHand(hand);
            if (itemInHand.isEmpty())
            {
                return InteractionResult.TRY_WITH_EMPTY_HAND;  // this is stupid
            }
            if (level.recipeAccess().propertySet(RecipePropertySet.CAMPFIRE_INPUT).test(itemInHand))
            {
                if (level instanceof ServerLevel serverLevel && canPlaceFood(campfire) && campfire.placeFood(serverLevel, player, itemInHand))
                {
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return InteractionResult.SUCCESS_SERVER;
                }
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }



    private static boolean canPlaceFood(CampfireBlockEntity campfireblockentity)
    {
        for (int i = 0; i < campfireblockentity.items.size(); ++i)
        {
            if (campfireblockentity.items.get(i).isEmpty())
            {
                return true;
            }
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        if (! BlockEntityTypes.CAMPFIRE.validBlocks.contains(this))
        {
            BlockEntityTypes.CAMPFIRE.validBlocks = new HashSet<>(BlockEntityTypes.CAMPFIRE.validBlocks);
            BlockEntityTypes.CAMPFIRE.validBlocks.add(this);
        }
        return BlockEntityTypes.CAMPFIRE.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> p_152757_)
    {
        if (level.isClientSide())
        {
            if (state.getValue(LIT))
            {
                return createTickerHelper(p_152757_, BlockEntityTypes.CAMPFIRE, FirepitBlock::ParticleTickOverride);
            }
            else
            {
                return createTickerHelper(p_152757_, BlockEntityTypes.CAMPFIRE, FirepitBlock::NoopTickOverride);
            }
        }
        else
        {
            if (state.getValue(LIT))
            {
                return createTickerHelper(p_152757_, BlockEntityTypes.CAMPFIRE, FirepitBlock::CookTickOverride);
            }
            else
            {
                return createTickerHelper(p_152757_, BlockEntityTypes.CAMPFIRE, FirepitBlock::CooldownTickOverride);
            }
        }
    }

    ///////////////////////////////////////////////////////////

    private static void CookTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e) {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
        // cooking function
        if (level instanceof ServerLevel sl)
        {
            if (quickCheck == null)
            {
                quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
            }
            CampfireBlockEntity.cookTick(sl, pos, state, e, quickCheck);
        }
        // check rain and dowse:
        if (level.getLevelData().getGameTime() % (2*20) == 13)
        {
            if (level.isRainingAt(pos.above()) && level.getRandom().nextInt(5) == 0 && level.canSeeSky(pos))
            {
                dowse((Entity)null, level, pos, state);
                level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(false)), 3);
            }
        }
    }
    private static RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck = null;

    private static void ParticleTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e)
    {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
        // now the particles:
        if (!e.items.get(0).isEmpty() && level.getRandom().nextFloat() < 0.2F)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.5D;
            double d2 = (double)pos.getZ() + 0.5D;

            if (level.getRandom().nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.getRandom().nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2-0.3, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.getRandom().nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2+0.3, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.getRandom().nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0-0.3, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.getRandom().nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0+0.3, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
        }
    }

    private static void CooldownTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e)
    {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
        // cooldown function
        CampfireBlockEntity.cooldownTick(level, pos, state, e);
    }

    private static void NoopTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e)
    {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
    }

    private static void CheckInitialization(CampfireBlockEntity e)
    {
        if (e.items.size() > 1)
        {
            ItemStack existing = e.items.get(0);
            e.items = NonNullList.withSize(1, ItemStack.EMPTY);
            e.items.set(0, existing);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
}
