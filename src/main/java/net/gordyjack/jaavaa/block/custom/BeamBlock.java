package net.gordyjack.jaavaa.block.custom;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import net.gordyjack.jaavaa.block.util.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

import javax.swing.text.html.*;

public class BeamBlock extends BlockPiece {
    public MapCodec<BeamBlock> CODEC = simpleCodec(BeamBlock::new);
    private static final int MIN = 5;
    private static final int MAX = 11;
    private static final int CAP_MIN = MIN - 2;
    private static final int CAP_MAX = MAX + 2;
    private static final int CAP_HEIGHT = 4;
    private static final VoxelShape X_SHAPE = Block.box(0, MIN, MIN, 16, MAX, MAX);
    private static final VoxelShape Y_SHAPE = Block.box(MIN, 0, MIN, MAX, 16, MAX);
    private static final VoxelShape Z_SHAPE = Block.box(MIN, MIN, 0, MAX, MAX, 16);
    private static final VoxelShape DOWN_CAP = Block.box(CAP_MIN, 0, CAP_MIN, CAP_MAX, CAP_HEIGHT, CAP_MAX);
    private static final VoxelShape UP_CAP = Block.box(CAP_MIN, 16 - CAP_HEIGHT, CAP_MIN, CAP_MAX, 16, CAP_MAX);
    private static final VoxelShape NORTH_CAP = Block.box(CAP_MIN, CAP_MIN, 0, CAP_MAX, CAP_MAX, CAP_HEIGHT);
    private static final VoxelShape SOUTH_CAP = Block.box(CAP_MIN, CAP_MIN, 16 - CAP_HEIGHT, CAP_MAX, CAP_MAX, 16);
    private static final VoxelShape WEST_CAP = Block.box(0, CAP_MIN, CAP_MIN, CAP_HEIGHT, CAP_MAX, CAP_MAX);
    private static final VoxelShape EAST_CAP = Block.box(16 - CAP_HEIGHT, CAP_MIN, CAP_MIN, 16, CAP_MAX, CAP_MAX);

    public static final EnumProperty<BeamCapType> BEAM_CAP_TYPE = JAAVAABlockProperties.BEAM_CAP_TYPE;

    public BeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(WATERLOGGED, false)
                        .setValue(BEAM_CAP_TYPE, BeamCapType.NONE)
        );
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(BEAM_CAP_TYPE);
        super.createBlockStateDefinition(builder);
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        Direction.Axis axis = state.getValue(FACING).getAxis();

        VoxelShape base = switch (axis) {
            case X -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
        BeamCapType caps = state.getValue(BEAM_CAP_TYPE);
        if (caps == BeamCapType.NONE) {
            return base;
        }
        // Precompute cap shapes per axis
        VoxelShape bottom = switch (axis) {
            case X -> WEST_CAP;
            case Y -> DOWN_CAP;
            case Z -> NORTH_CAP;
        };

        VoxelShape top = switch (axis) {
            case X -> EAST_CAP;
            case Y -> UP_CAP;
            case Z -> SOUTH_CAP;
        };
        return switch (caps) {
            case BOTTOM -> Shapes.or(base, bottom);
            case TOP -> Shapes.or(base, top);
            case BOTH -> Shapes.or(base, bottom, top);
            default -> base;
        };
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        BlockState baseState = this.defaultBlockState()
                .setValue(FACING, ctx.getClickedFace().getOpposite())
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));

        return this.updateCapState(baseState, level, pos);
    }
    @Override
    protected boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }
    //Waterloggable overrides
    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity livingEntity, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return super.canPlaceLiquid(livingEntity, blockGetter, blockPos, blockState, fluid);
    }
    @Override
    protected @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return super.placeLiquid(levelAccessor, blockPos, blockState, fluidState);
    }
    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        Direction.Axis axis = blockState.getValue(FACING).getAxis();

        // Only neighbors along the beam's axis matter for caps
        if (direction.getAxis() == axis) {
            blockState = updateCapState(blockState, levelReader, blockPos);
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }
    // ------------------------------------------------------------------------
    // PATHFINDING
    // ------------------------------------------------------------------------
    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> false;
            case WATER -> state.getValue(WATERLOGGED);
        };
    }

    private BeamCapType determineCapType(boolean bottom, boolean top) {
        if (bottom && top) {
            return BeamCapType.BOTH;
        } else if (bottom) {
            return BeamCapType.BOTTOM;
        } else if (top) {
            return BeamCapType.TOP;
        } else {
            return BeamCapType.NONE;
        }
    }
    private BlockState updateCapState(BlockState state, LevelReader level, BlockPos pos) {
        var axis = state.getValue(FACING).getAxis();

        Direction bottomDir = axis.getNegative();
        Direction topDir = axis.getPositive();

        boolean hasBottomNeighbor = level.getBlockState(pos.relative(bottomDir))
                .isFaceSturdy(level, pos.relative(bottomDir), bottomDir.getOpposite());
        boolean hasTopNeighbor = level.getBlockState(pos.relative(topDir))
                .isFaceSturdy(level, pos.relative(topDir), topDir.getOpposite());

        return state.setValue(BEAM_CAP_TYPE, determineCapType(hasBottomNeighbor, hasTopNeighbor));
    }
}
