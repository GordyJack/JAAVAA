package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.gordyjack.jaavaa.block.custom.entity.AllayCollectorEntity;
import net.gordyjack.jaavaa.block.custom.entity.JAAVAABlockEntityTypes;
import net.gordyjack.jaavaa.block.util.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class AllayCollectorBlock
extends BlockWithEntity
implements Waterloggable,
VoxelShapeUtils {
    //Fields
    private static final MapCodec<AllayCollectorBlock> CODEC = AllayCollectorBlock.createCodec(settings -> new AllayCollectorBlock(settings, () -> JAAVAABlockEntityTypes.ALLAY_COLLECTOR));
    public static final DirectionProperty FACING = FacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty ENABLED = Properties.ENABLED;
    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(-0.05, -1, -0.05, 16.05, 17, 16.05),
            Block.createCuboidShape(2, -3, 2, 14, -1, 14),
            Block.createCuboidShape(2, 17, 2, 14, 19, 14)
                                                     ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    //Constructor
    public AllayCollectorBlock(Settings settings, Supplier<BlockEntityType<? extends AllayCollectorEntity>> supplier) {
        super(settings);
    }

    //Inherrited Methods
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, ENABLED);
    }
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AllayCollectorEntity(pos, state);
    }
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof AllayCollectorEntity collectorEntity)) {
            return 0;
        }
        return collectorEntity.getStack().isEmpty() ? 0 : 15;
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FACING).getAxis() != Direction.Axis.Y ? SHAPE : rotateShape(SHAPE, Direction.DOWN);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide().getOpposite();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(ENABLED, true);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : validateTicker(type, JAAVAABlockEntityTypes.ALLAY_COLLECTOR, AllayCollectorEntity::tick);
    }
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (world.isClient() || !(blockEntity instanceof AllayCollectorEntity collectorEntity)) {
            return ActionResult.CONSUME;
        }

        ItemStack heldStack = player.getStackInHand(hand);
        ItemStack filterStack = collectorEntity.getStack();

        if (!filterStack.isEmpty()) {
            collectorEntity.clear();
        } else {
            collectorEntity.setStack(heldStack.copyWithCount(1));
        }
        collectorEntity.markDirty();
        return ActionResult.SUCCESS;
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        this.updateEnabled(world, pos, state);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    //Helper Methods
    public static <B extends Block> MapCodec<B> createCodec(Function<Settings, B> blockFromSettings) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(AbstractBlock.createSettingsCodec()).apply(instance, blockFromSettings));
    }
    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean shouldBeEnabled = !world.isReceivingRedstonePower(pos);
        if (shouldBeEnabled != state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, shouldBeEnabled), Block.NOTIFY_LISTENERS);
        }
    }
}
