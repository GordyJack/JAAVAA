package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.MapCodec;
import net.gordyjack.jaavaa.block.util.JAAVAABlockProperties;
import net.gordyjack.jaavll.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Blocktant
        extends BlockPiece {

    // ------------------------------------------------------------------------
    // CONSTANTS + SHAPE TABLE
    // ------------------------------------------------------------------------

    private static final MapCodec<Blocktant> CODEC = simpleCodec(Blocktant::new);

    private static final VoxelShape POS_0___1_DNW_SHAPE = box(0, 0, 0, 8, 8, 8);
    private static final VoxelShape POS_1___2_DNE_SHAPE = box(8, 0, 0, 16, 8, 8);
    private static final VoxelShape POS_2___4_DSW_SHAPE = box(0, 0, 8, 8, 8, 16);
    private static final VoxelShape POS_3___8_DSE_SHAPE = box(8, 0, 8, 16, 8, 16);
    private static final VoxelShape POS_4__16_UNW_SHAPE = box(0, 8, 0, 8, 16, 8);
    private static final VoxelShape POS_5__32_UNE_SHAPE = box(8, 8, 0, 16, 16, 8);
    private static final VoxelShape POS_6__64_USW_SHAPE = box(0, 8, 8, 8, 16, 16);
    private static final VoxelShape POS_7_128_USE_SHAPE = box(8, 8, 8, 16, 16, 16);

    private static final VoxelShape[] ATOMICS = {
            POS_0___1_DNW_SHAPE,
            POS_1___2_DNE_SHAPE,
            POS_2___4_DSW_SHAPE,
            POS_3___8_DSE_SHAPE,
            POS_4__16_UNW_SHAPE,
            POS_5__32_UNE_SHAPE,
            POS_6__64_USW_SHAPE,
            POS_7_128_USE_SHAPE
    };

    private static final Map<Direction, List<VoxelShape>> SHAPE_TABLE = new EnumMap<>(Direction.class);

    static {
        for (Direction face : Direction.values()) {
            VoxelShape[] rotated = new VoxelShape[8];
            for (int bit = 0; bit < 8; bit++) {
                rotated[bit] = VoxelShapeUtils.rotateShape(ATOMICS[bit], face);
            }

            List<VoxelShape> list = new ArrayList<>(256);
            for (int mask = 0; mask < 256; mask++) {
                VoxelShape s = Shapes.empty();
                for (int bit = 0; bit < 8; bit++) {
                    if ((mask & (1 << bit)) != 0) {
                        s = Shapes.or(s, rotated[bit]);
                    }
                }
                list.add(s);
            }
            SHAPE_TABLE.put(face, List.copyOf(list));
        }
    }
    // The 8 bit-values
    public static final int POS_1 = 0b00000001;
    public static final int POS_2 = 0b00000010;
    public static final int POS_3 = 0b00000100;
    public static final int POS_4 = 0b00001000;
    public static final int POS_5 = 0b00010000;
    public static final int POS_6 = 0b00100000;
    public static final int POS_7 = 0b01000000;
    public static final int POS_8 = 0b10000000;

    // ------------------------------------------------------------------------
    // BLOCKSTATE PROPERTIES
    // ------------------------------------------------------------------------

    // Boolean properties (multipart-friendly)
    public static final BooleanProperty POS_1_ON = JAAVAABlockProperties.POS_1_ON;
    public static final BooleanProperty POS_2_ON = JAAVAABlockProperties.POS_2_ON;
    public static final BooleanProperty POS_3_ON = JAAVAABlockProperties.POS_3_ON;
    public static final BooleanProperty POS_4_ON = JAAVAABlockProperties.POS_4_ON;
    public static final BooleanProperty POS_5_ON = JAAVAABlockProperties.POS_5_ON;
    public static final BooleanProperty POS_6_ON = JAAVAABlockProperties.POS_6_ON;
    public static final BooleanProperty POS_7_ON = JAAVAABlockProperties.POS_7_ON;
    public static final BooleanProperty POS_8_ON = JAAVAABlockProperties.POS_8_ON;

    // ------------------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------------------

    public Blocktant(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(POS_1_ON, false)
                        .setValue(POS_2_ON, false)
                        .setValue(POS_3_ON, false)
                        .setValue(POS_4_ON, false)
                        .setValue(POS_5_ON, false)
                        .setValue(POS_6_ON, false)
                        .setValue(POS_7_ON, false)
                        .setValue(POS_8_ON, false)
                        .setValue(WATERLOGGED, false)
        );
    }

    // ------------------------------------------------------------------------
    // BLOCKSTATE DEFINITION
    // ------------------------------------------------------------------------

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(
                POS_1_ON, POS_2_ON, POS_3_ON, POS_4_ON,
                POS_5_ON, POS_6_ON, POS_7_ON, POS_8_ON
        );
        super.createBlockStateDefinition(builder);
    }

    // ------------------------------------------------------------------------
    // INTERNAL HELPER — convert 8 booleans → bitmask
    // ------------------------------------------------------------------------

    private int getMask(BlockState s) {
        int mask = 0;
        if (s.getValue(POS_1_ON)) mask |= POS_1;
        if (s.getValue(POS_2_ON)) mask |= POS_2;
        if (s.getValue(POS_3_ON)) mask |= POS_3;
        if (s.getValue(POS_4_ON)) mask |= POS_4;
        if (s.getValue(POS_5_ON)) mask |= POS_5;
        if (s.getValue(POS_6_ON)) mask |= POS_6;
        if (s.getValue(POS_7_ON)) mask |= POS_7;
        if (s.getValue(POS_8_ON)) mask |= POS_8;
        return mask;
    }

    private boolean allBitsPresent(BlockState s) {
        return s.getValue(POS_1_ON) &&
                s.getValue(POS_2_ON) &&
                s.getValue(POS_3_ON) &&
                s.getValue(POS_4_ON) &&
                s.getValue(POS_5_ON) &&
                s.getValue(POS_6_ON) &&
                s.getValue(POS_7_ON) &&
                s.getValue(POS_8_ON);
    }

    // ------------------------------------------------------------------------
    // SHAPE + PLACEMENT
    // ------------------------------------------------------------------------

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        Direction facing = state.getValue(FACING);
        int mask = getMask(state);
        return SHAPE_TABLE.get(facing).get(mask);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (allBitsPresent(state) || !ctx.getItemInHand().is(this.asItem()))
            return false;

        if (ctx.replacingClickedOnBlock()) {
            int pos = getPlacePosition(ctx);
            return switch (pos) {
                case POS_1 -> !state.getValue(POS_1_ON);
                case POS_2 -> !state.getValue(POS_2_ON);
                case POS_3 -> !state.getValue(POS_3_ON);
                case POS_4 -> !state.getValue(POS_4_ON);
                case POS_5 -> !state.getValue(POS_5_ON);
                case POS_6 -> !state.getValue(POS_6_ON);
                case POS_7 -> !state.getValue(POS_7_ON);
                case POS_8 -> !state.getValue(POS_8_ON);
                default -> false;
            };
        }

        return true;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState old = ctx.getLevel().getBlockState(ctx.getClickedPos());
        FluidState fluid = ctx.getLevel().getFluidState(ctx.getClickedPos());

        Direction facing = old.is(this)
                ? old.getValue(FACING)
                : ctx.getClickedFace().getOpposite();

        BlockState result = this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, false);

        // Merge old shape
        if (old.is(this)) {
            result = result
                    .setValue(POS_1_ON, old.getValue(POS_1_ON))
                    .setValue(POS_2_ON, old.getValue(POS_2_ON))
                    .setValue(POS_3_ON, old.getValue(POS_3_ON))
                    .setValue(POS_4_ON, old.getValue(POS_4_ON))
                    .setValue(POS_5_ON, old.getValue(POS_5_ON))
                    .setValue(POS_6_ON, old.getValue(POS_6_ON))
                    .setValue(POS_7_ON, old.getValue(POS_7_ON))
                    .setValue(POS_8_ON, old.getValue(POS_8_ON));
        }

        int bit = getPlacePosition(ctx);

        // Apply the new octant
        result = switch (bit) {
            case POS_1 -> result.setValue(POS_1_ON, true);
            case POS_2 -> result.setValue(POS_2_ON, true);
            case POS_3 -> result.setValue(POS_3_ON, true);
            case POS_4 -> result.setValue(POS_4_ON, true);
            case POS_5 -> result.setValue(POS_5_ON, true);
            case POS_6 -> result.setValue(POS_6_ON, true);
            case POS_7 -> result.setValue(POS_7_ON, true);
            case POS_8 -> result.setValue(POS_8_ON, true);
            default -> result;
        };

        // waterlog only if *not* full
        if (!allBitsPresent(result)) {
            result = result.setValue(WATERLOGGED, fluid.is(Fluids.WATER));
        }

        return result;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return allBitsPresent(state);
    }

    // ------------------------------------------------------------------------
    // WATERLOGGING
    // ------------------------------------------------------------------------

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (this.allBitsPresent(state)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluid) {
        return !allBitsPresent(state) && super.placeLiquid(level, pos, state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity entity, BlockGetter getter, BlockPos pos, BlockState state, Fluid fluid) {
        return !allBitsPresent(state) && super.canPlaceLiquid(entity, getter, pos, state, fluid);
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, LevelReader reader, ScheduledTickAccess tick, BlockPos pos, Direction dir, BlockPos neighborPos, BlockState neighborState, RandomSource rng) {
        if (state.getValue(WATERLOGGED)) {
            tick.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(reader));
        }
        return super.updateShape(state, reader, tick, pos, dir, neighborPos, neighborState, rng);
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

    // ------------------------------------------------------------------------
    // HIT DETECTION → OCTANT BIT
    // ------------------------------------------------------------------------

    public int getPlacePosition(BlockPlaceContext ctx) {
        Vec3 hit = ctx.getClickLocation();
        BlockPos pos = ctx.getClickedPos();
        BlockState existing = ctx.getLevel().getBlockState(pos);

        Direction hitSide = ctx.getClickedFace();
        Direction facing = existing.is(this)
                ? existing.getValue(FACING)
                : hitSide.getOpposite();

        double x = hit.x - pos.getX();
        double y = hit.y - pos.getY();
        double z = hit.z - pos.getZ();

        if (x == 0.5 && hitSide == Direction.EAST)  x = Math.nextUp(x);
        if (y == 0.5 && hitSide == Direction.UP)    y = Math.nextUp(y);
        if (z == 0.5 && hitSide == Direction.SOUTH) z = Math.nextUp(z);

        boolean isEast  = x > 0.5;
        boolean isUp    = y > 0.5;
        boolean isSouth = z > 0.5;

        boolean isDown  = !isUp;
        boolean isWest  = !isEast;
        boolean isNorth = !isSouth;

        return getPosition(
                facing,
                isUp, isDown,
                isNorth, isSouth,
                isWest, isEast
        );
    }

    private int getPosition(
            Direction facing,
            boolean up, boolean down,
            boolean north, boolean south,
            boolean west, boolean east
    ) {

        final int DNW = POS_1;
        final int DNE = POS_2;
        final int DSW = POS_3;
        final int DSE = POS_4;
        final int UNW = POS_5;
        final int UNE = POS_6;
        final int USW = POS_7;
        final int USE = POS_8;

        return switch (facing) {
            case DOWN -> {
                if (down) {
                    if (north) yield west ? DNW : DNE;
                    else       yield west ? DSW : DSE;
                } else {
                    if (north) yield west ? UNW : UNE;
                    else       yield west ? USW : USE;
                }
            }
            case UP -> {
                if (up) {
                    if (south) yield west ? DNW : DNE;
                    else       yield west ? DSW : DSE;
                } else {
                    if (south) yield west ? UNW : UNE;
                    else       yield west ? USW : USE;
                }
            }
            case NORTH -> {
                if (north) {
                    if (up) yield west ? DNW : DNE;
                    else    yield west ? DSW : DSE;
                } else {
                    if (up) yield west ? UNW : UNE;
                    else    yield west ? USW : USE;
                }
            }
            case SOUTH -> {
                if (south) {
                    if (up) yield east ? DNW : DNE;
                    else    yield east ? DSW : DSE;
                } else {
                    if (up) yield east ? UNW : UNE;
                    else    yield east ? USW : USE;
                }
            }
            case WEST -> {
                if (west) {
                    if (up) yield south ? DNW : DNE;
                    else    yield south ? DSW : DSE;
                } else {
                    if (up) yield south ? UNW : UNE;
                    else    yield south ? USW : USE;
                }
            }
            case EAST -> {
                if (east) {
                    if (up) yield north ? DNW : DNE;
                    else    yield north ? DSW : DSE;
                } else {
                    if (up) yield north ? UNW : UNE;
                    else    yield north ? USW : USE;
                }
            }
        };
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }
}