package moonfather.woodentoolsremoved.items.bowl;

import moonfather.woodentoolsremoved.Constants;
import moonfather.woodentoolsremoved.RegistryManager;
import moonfather.woodentoolsremoved.other.TetraSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.Nullable;

public class BowlBlock extends Block
{
    public static final Component TooltipLine1 = Component.translatable("item.woodentoolsremoved.powder_bowl.tooltip1").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));
    public static final Component TooltipLine2 = Component.translatable("item.woodentoolsremoved.powder_bowl.tooltip2").withStyle(Style.EMPTY.withColor(Constants.COLOR_GRAY_TOOLTIPS));

    public BowlBlock(ResourceKey<Block> blockResourceKey)
    {
        super(Properties.of().instabreak().pushReaction(PushReaction.DESTROY).explosionResistance(1e-5f).ignitedByLava().forceSolidOff().setId(blockResourceKey));
    }

    public static Item.Properties getItemProperties()
    {
        return new Item.Properties().craftRemainder(Items.BOWL);
    }



    private static final VoxelShape SHAPE_BOWL = Block.box(3d, 0.0D, 3d, 13d, 2d, 13.d);
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return SHAPE_BOWL;
    }


    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult)
    {
        if (this.IsProperActivationItem(itemStack))
        {
            if (! level.isClientSide())
            {
                this.updateUsedItem(itemStack, player, hand);
                // ekusproshion
                this.boom(level, blockPos);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, blockState, level, blockPos, player, hand, blockHitResult);
    }



    private boolean IsProperActivationItem(ItemStack stack)
    {
        if (stack.is(Constants.Tags.IGNITES_GUNPOWDER) || stack.getItem() instanceof FlintAndSteelItem)
        {
            return true;
        }
        if (TetraSupport.IsTetraFlintItem(stack))
        {
            return true;
        }
        return false;
    }


    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        if (level instanceof ServerLevel)
        {
            int chance = (direction == Direction.DOWN || direction == Direction.UP) ? 95 : 25;
            if (((ServerLevel)level).getRandom().nextInt(100) < chance)
            {
                this.boom((ServerLevel)level, pos);
            }
        }

        return super.getFireSpreadSpeed(state, level, pos, direction);
    }



    private void boom(Level level, BlockPos pos)
    {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        float force = 0.7F;
        level.explode(null, pos.getX()+0.5f, pos.getY()+0.3f, pos.getZ()+0.5f, force, Level.ExplosionInteraction.NONE);
        BlockPos.MutableBlockPos pos2 = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());
        this.tryDestroyStone(level, pos2.move( 0, -1, 0 ), 100);
        this.tryDestroyStone(level, pos2.move( 0, +2,  0), 100);
        this.tryDestroyStone(level, pos2.move(-1, -1,  0),  90);
        this.tryDestroyStone(level, pos2.move(+2,  0,  0),  90);
        this.tryDestroyStone(level, pos2.move(-1,  0, -1),  90);
        this.tryDestroyStone(level, pos2.move( 0,  0, +2),  90);
    }


    private final TagKey<Block> cobblestoneTag = BlockTags.create(Identifier.fromNamespaceAndPath("c","cobblestone"));

    private void tryDestroyStone(Level level, BlockPos pos, int chancePercentage)
    {
        if (level.getRandom().nextInt(100) < chancePercentage)
        {
            BlockState state = level.getBlockState(pos);
            if (! state.isAir() && state.getBlock().getExplosionResistance() <= 6 && ! state.is(BlockTags.NEEDS_IRON_TOOL) && ! state.is(BlockTags.NEEDS_DIAMOND_TOOL))
            {
                boolean baseStone = state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(Tags.Blocks.ORES_COAL) || state.is(cobblestoneTag);
                boolean drop = baseStone || level.getRandom().nextInt(100) < 10;    // 10% maybe configurable
                level.destroyBlock(pos, drop);
            }
        }
    }



    @Override
    public boolean onCaughtFire(BlockState state, Level level, BlockPos pos, @org.jspecify.annotations.Nullable Direction direction, @org.jspecify.annotations.Nullable LivingEntity igniter)
    {
        this.boom(level, pos);
        return true;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston)
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
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos)
    {
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
    public boolean dropFromExplosion(Explosion p_57427_)
    {
        return false;
    }

    @Override
    public void onProjectileHit(Level level, BlockState p_57430_, BlockHitResult p_57431_, Projectile p_57432_)
    {
        if (! level.isClientSide() && level instanceof ServerLevel sl)
        {
            BlockPos blockpos = p_57431_.getBlockPos();
            Entity entity = p_57432_.getOwner();
            if (p_57432_.isOnFire() && p_57432_.mayInteract(sl, blockpos))
            {
                onCaughtFire(p_57430_, level, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                level.removeBlock(blockpos, false);
            }
        }
    }

    private void updateUsedItem(ItemStack stack, Player player, InteractionHand hand)
    {
        if (! player.hasInfiniteMaterials())
        {
            if (stack.isDamageableItem())
            {
                stack.hurtAndBreak(1, player, hand.equals(InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }
            else if (stack.is(Items.FIRE_CHARGE))
            {
                stack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        }
    }


    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player)
    {
        return RegistryManager.ItemBlackPowderBowl.get().getDefaultInstance();
    }
}
