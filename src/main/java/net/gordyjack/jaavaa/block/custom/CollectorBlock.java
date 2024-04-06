package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.*;
import net.gordyjack.jaavaa.block.custom.entity.*;
import net.gordyjack.jaavaa.block.util.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.function.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.function.*;
import java.util.stream.*;

public class CollectorBlock
        extends BlockWithEntity
        implements Waterloggable,
        VoxelShapeUtils {
    //Fields
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty ENABLED = Properties.ENABLED;
    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(-0.05, -1, -0.05, 16.05, 17, 16.05),
            Block.createCuboidShape(2, -3, 2, 14, -1, 14),
            Block.createCuboidShape(2, 17, 2, 14, 19, 14)
                                                     ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    private final Supplier<BlockEntityType<? extends AbstractCollectorEntity>> SUPPLIER;

    //Constructor
    public CollectorBlock(Settings settings, Supplier<BlockEntityType<? extends AbstractCollectorEntity>> supplier) {
        super(settings);
        this.SUPPLIER = supplier;
    }

    public CollectorBlock(Settings settings) {
        this(settings, null);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return SUPPLIER != null ? createCodec(CollectorBlock::new) : null;
    }

    //Inherrited Methods
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, ENABLED);
    }
    @Override
    public boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return SUPPLIER.get().instantiate(pos, state);
    }
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractCollectorEntity collectorEntity)) {
            return 0;
        }
        return collectorEntity.getFilter().isEmpty() ? 0 : 15;
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
        if (SUPPLIER.get() == JAAVAABlockEntityTypes.ALLAY_COLLECTOR) {
            return validateTicker(type, JAAVAABlockEntityTypes.ALLAY_COLLECTOR, AllayCollectorEntity::tick);
        }
        if (SUPPLIER.get() == JAAVAABlockEntityTypes.ENDER_COLLECTOR) {
            return validateTicker(type, JAAVAABlockEntityTypes.ENDER_COLLECTOR, EnderCollectorEntity::tick);
        }
        return null;
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
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var blockEntity = world.getBlockEntity(pos);
        if (world.isClient || !(blockEntity instanceof AbstractCollectorEntity collectorEntity)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        var filterStack = collectorEntity.getFilter();
        if (!filterStack.isEmpty()) {
            collectorEntity.clear();
        } else {
            collectorEntity.setFilter(stack.copyWithCount(1));
        }
        return ItemActionResult.success(true);
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
    private void updateEnabled(World world, BlockPos pos, BlockState state) {
        boolean shouldBeEnabled = !world.isReceivingRedstonePower(pos);
        if (shouldBeEnabled != state.get(ENABLED)) {
            world.setBlockState(pos, state.with(ENABLED, shouldBeEnabled), Block.NOTIFY_LISTENERS);
        }
    }
}
