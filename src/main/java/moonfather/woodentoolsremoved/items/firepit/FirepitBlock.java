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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;

public class FirepitBlock extends CampfireBlock
{
    public FirepitBlock()
    {
        super(false /*spawnParticles*/, 0 /*fireDamage*/, BlockBehaviour.Properties.of(Material.DECORATION, MaterialColor.PODZOL).strength(1.0F).sound(SoundType.GRAVEL).lightLevel(litBlockEmission(7)).noOcclusion());
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue)
    {
        return (state) ->
        {
            return state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
        };
    }



    public static Item.Properties GetItemProperties()
    {
        return new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).stacksTo(1);
    }



    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean dontknow)
    {
        super.neighborChanged(state, level, pos, block, pos2, dontknow);
        if (pos2.getY() == pos.getY() - 1)
        {
            BlockState below = level.getBlockState(pos2);
            if (! level.isClientSide && ! below.isFaceSturdy(level, pos2, Direction.UP, SupportType.CENTER))
            {
                level.destroyBlock(pos, true);
            }
        }
        if (pos2.getY() == pos.getY())
        {
            FluidState newBlock = level.getFluidState(pos2);
            if (! level.isClientSide && ! newBlock.is(Fluids.EMPTY))
            {
                level.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        if (pos.getY() <= level.getMinBuildHeight())
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
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state)
    {
        return RegistryManager.ItemFirepit.get().getDefaultInstance();
    }



    private static final VoxelShape SHAPE_BEAM = Block.box(2d, 0.0D, 2d, 14d, 11d, 14d);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return SHAPE_BEAM;
    }



    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable BlockGetter blockGetter, List<Component> lines, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, blockGetter, lines, tooltipFlag);
        if (OptionsHolder.COMMON.EnableFirestarter.get())
        {
            lines.add(TooltipForFirepitLine1);
            lines.add(TooltipForFirepitLine2);
        }
    }
    private static final Component TooltipForFirepitLine1 = new TranslatableComponent("item.woodentoolsremoved.firepit.tooltip1").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));
    private static final Component TooltipForFirepitLine2 = new TranslatableComponent("item.woodentoolsremoved.firepit.tooltip2").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));

    ///////////////////////////////////////////***********************

    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult)
    {
        BlockEntity blockentity = level.getBlockEntity(blockPos);
        if (blockentity instanceof CampfireBlockEntity)
        {
            CampfireBlockEntity campfireblockentity = (CampfireBlockEntity)blockentity;
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.isEmpty() && player.isCrouching())
            {
                if (state.getValue(CampfireBlock.LIT))
                {
                    if (!level.isClientSide()) {
                        level.playSound((Player)null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    dowse((Entity)null, level, blockPos, state);
                    level.setBlock(blockPos, state.setValue(LIT, Boolean.valueOf(false)), 3);
                }
                else if (! OptionsHolder.COMMON.EnableFirestarter.get())
                {
                    // can light with empty hand
                    campfireblockentity.dowse();
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                    level.setBlock(blockPos, state.setValue(LIT, Boolean.valueOf(true)), 3);
                }
                else
                {
                    // need firestarter
                    player.displayClientMessage(ERROR_NEED_TOOL, true);
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
            Optional<CampfireCookingRecipe> optional = campfireblockentity.getCookableRecipe(itemstack);
            if (optional.isPresent())
            {
                if (FirepitBlock.CanPlaceFood(campfireblockentity))
                {
                    if (!level.isClientSide)
                    {
                        campfireblockentity.placeFood(player.getAbilities().instabuild ? itemstack.copy() : itemstack, optional.get().getCookingTime() * 2);
                        player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.PASS;
    }
    private static final Component ERROR_NEED_TOOL = new TranslatableComponent("item.woodentoolsremoved.firepit.error").withStyle(Style.EMPTY.withColor(Constants.COLOR_MESSAGE_GRAY));

    private static boolean CanPlaceFood(CampfireBlockEntity campfireblockentity)
    {
        for(int i = 0; i < campfireblockentity.items.size(); ++i)
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
        if (! BlockEntityType.CAMPFIRE.validBlocks.contains(this))
        {
            BlockEntityType.CAMPFIRE.validBlocks = new HashSet<>(BlockEntityType.CAMPFIRE.validBlocks);
            BlockEntityType.CAMPFIRE.validBlocks.add(this);
        }
        return BlockEntityType.CAMPFIRE.create(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> p_152757_)
    {
        if (level.isClientSide)
        {
            if (state.getValue(LIT))
            {
                return createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, FirepitBlock::ParticleTickOverride);
            }
            else
            {
                return createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, FirepitBlock::NoopTickOverride);
            }
        }
        else
        {
            if (state.getValue(LIT))
            {
                return createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, FirepitBlock::CookTickOverride);
            }
            else
            {
                return createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, FirepitBlock::CooldownTickOverride);
            }
        }
    }

    ///////////////////////////////////////////////////////////

    private static void CookTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e)
    {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
        // cooking function
        CampfireBlockEntity.cookTick(level, pos, state, e);
        // check rain amd dowse:
        if (level.getLevelData().getGameTime() % (2*20) == 13)
        {
            if (level.getLevelData().isRaining() && level.random.nextInt(5) == 0 && level.canSeeSky(pos))
            {
                dowse((Entity)null, level, pos, state);
                level.setBlock(pos, state.setValue(LIT, Boolean.valueOf(false)), 3);
            }
        }
    }

    private static void ParticleTickOverride(Level level, BlockPos pos, BlockState state, CampfireBlockEntity e)
    {
        // check initialization (we can't do it in newBlockEntity, it doesn't trigger after reload for saved BEs)
        FirepitBlock.CheckInitialization(e);
        // now the particles:
        if (!e.items.get(0).isEmpty() && level.random.nextFloat() < 0.2F)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.5D;
            double d2 = (double)pos.getZ() + 0.5D;

            if (level.random.nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.random.nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2-0.3, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.random.nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2+0.3, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.random.nextFloat() < 0.2F)
            {
                level.addParticle(ParticleTypes.SMOKE, d0-0.3, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
            if (level.random.nextFloat() < 0.2F)
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
}
