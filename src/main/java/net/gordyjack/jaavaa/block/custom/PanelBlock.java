package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

public class PanelBlock extends BlockPiece {
    private final MapCodec<PanelBlock> CODEC = simpleCodec(PanelBlock::new);

    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

    public PanelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(WATERLOGGED, false)
                        .setValue(LAYERS, 1)
        );
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(LAYERS);
        super.createBlockStateDefinition(builder);
    }
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        boolean isPanelItem = ctx.getItemInHand().is(this.asItem());
        boolean clickedTop = ctx.getClickedFace() == state.getValue(FACING).getOpposite();

        if (isPanelItem && !this.layersFull(state)) {
            return !ctx.replacingClickedOnBlock() || clickedTop;
        }
        return false;
    }
    @Override
    protected float getShadeBrightness(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        int layersInPixels = state.getValue(LAYERS) * 2;
        return switch (state.getValue(FACING)) {
            case DOWN -> box(0, 0, 0, 16, layersInPixels, 16);
            case UP -> box(0, 16 - layersInPixels, 0, 16, 16, 16);
            case NORTH -> box(0, 0, 0, 16, 16, layersInPixels);
            case SOUTH -> box(0,0, 16 - layersInPixels, 16, 16, 16);
            case WEST -> box(0, 0, 0, layersInPixels, 16, 16);
            case EAST -> box(16 - layersInPixels, 0, 0, 16, 16, 16);
        };
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState existingState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        BlockState newState = super.getStateForPlacement(ctx);
        int layers = existingState.is(this) ? existingState.getValue(LAYERS) + 1 : 1;
        return newState.setValue(LAYERS, Math.min(layers, 8));
    }
    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return !this.layersFull(state);
    }
    // ------------------------------------------------------------------------
    // WATERLOGGING
    // ------------------------------------------------------------------------
    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (this.layersFull(state)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluid) {
        return !this.layersFull(state) && super.placeLiquid(level, pos, state, fluid);
    }
    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity entity, BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
        return !this.layersFull(state) && super.canPlaceLiquid(entity, getter, pos, state, fluid);
    }
    @Override
    protected @NotNull BlockState updateShape(BlockState state, LevelReader reader, ScheduledTickAccess tick, BlockPos pos, Direction dir, BlockPos neighborPos, BlockState neighborState, RandomSource rng) {
        if (state.getValue(WATERLOGGED)) {
            tick.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(reader));
        }
        return super.updateShape(state, reader, tick, pos, dir, neighborPos, neighborState, rng);
    }
    //Helpers ----------------------------------------------------------------
    private boolean layersFull(BlockState state) {
        return state.getValue(LAYERS) >= 8;
    }
}
