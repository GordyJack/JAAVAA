package net.gordyjack.jaavaa.blocks;

import net.gordyjack.jaavaa.blocks.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.world.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.jetbrains.annotations.*;

@SuppressWarnings("deprecation")
public class MiniBlock
extends Block
implements Waterloggable, VoxelShapeUtils {
    private static final IntProperty POSITION = ModBlockProperties.MINI_BLOCK_POSITION;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final Block BASE_BLOCK;
    private final BlockState BASE_BLOCK_STATE;
    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 8, 8);
    
    public MiniBlock(Block baseBlock, Settings settings) {
        super(settings);
        this.BASE_BLOCK = baseBlock;
        this.BASE_BLOCK_STATE = this.BASE_BLOCK.getDefaultState();
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int existingPosition = state.get(POSITION);
        VoxelShape returnShape = Block.createCuboidShape(0, 0, 0, 0, 0, 0);
        if ((existingPosition & (1 << 0)) != 0) { //Down North West
            returnShape = mergeShapes(returnShape, BASE_SHAPE);
        }
        if ((existingPosition & (1 << 1)) != 0) { //Down North East
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.EAST, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 2)) != 0) { //Down South West
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.SOUTH, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 3)) != 0) { //Down South East
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.EAST, 8);
            translatedShape = translateShape(translatedShape, Direction.SOUTH, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 4)) != 0) { //Up North West
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.UP, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 5)) != 0) { //Up North East
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.UP, 8);
            translatedShape = translateShape(translatedShape, Direction.EAST, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 6)) != 0) { //Up South West
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.UP, 8);
            translatedShape = translateShape(translatedShape, Direction.SOUTH, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        if ((existingPosition & (1 << 7)) != 0) { //Up South East
            VoxelShape translatedShape = translateShape(BASE_SHAPE, Direction.UP, 8);
            translatedShape = translateShape(translatedShape, Direction.EAST, 8);
            translatedShape = translateShape(translatedShape, Direction.SOUTH, 8);
            returnShape = mergeShapes(returnShape, translatedShape);
        }
        
        return returnShape;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        
        BlockState existingState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        int existingPosition = existingState.getBlock() == blockState.getBlock() ? existingState.get(POSITION) : 0;
        int placePosition = getPlacePosition(ctx);
        int newPosition = existingPosition | placePosition;
        
        return blockState.with(POSITION, newPosition)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    private static int getPlacePosition(ItemPlacementContext ctx) {
        Vec3d hitPos = ctx.getHitPos();
        double xHitPos = hitPos.x;
        double yHitPos = hitPos.y;
        double zHitPos = hitPos.z;
        
        BlockPos blockPos = ctx.getBlockPos();
        double xBlockPos = blockPos.getX();
        double yBlockPos = blockPos.getY();
        double zBlockPos = blockPos.getZ();
        
        double relativeXPos = xHitPos - xBlockPos;
        double relativeYPos = yHitPos - yBlockPos;
        double relativeZPos = zHitPos - zBlockPos;
        
        Direction side = ctx.getSide();
        if (relativeZPos == 0.5 && side == Direction.SOUTH) {
            relativeZPos = Math.nextUp(relativeZPos);
        }
        if (relativeYPos == 0.5 && side == Direction.UP) {
            relativeYPos = Math.nextUp(relativeYPos);
        }
        if (relativeXPos == 0.5 && side == Direction.EAST) {
            relativeXPos = Math.nextUp(relativeXPos);
        }
        
        boolean north = relativeZPos <= .5;
        boolean south = relativeZPos > .5;
        boolean down = relativeYPos <=.5;
        boolean up = relativeYPos > .5;
        boolean west = relativeXPos <= .5;
        boolean east = relativeXPos > .5;
        
        int placePosition = 0;
        
        if (down && north && west) {
            placePosition = 0b00000001;
        } else if (down && north && east) {
            placePosition = 0b00000010;
        } else if (down && south && west) {
            placePosition = 0b00000100;
        } else if (down && south && east) {
            placePosition = 0b00001000;
        } else if (up && north && west) {
            placePosition = 0b00010000;
        } else if (up && north && east) {
            placePosition = 0b00100000;
        } else if (up && south && west) {
            placePosition = 0b01000000;
        } else if (up && south && east) {
            placePosition = 0b10000000;
        }
        return placePosition;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POSITION, WATERLOGGED);
    }
    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(POSITION) != 0b11111111;
    }
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int placePosition = getPlacePosition(context);
        int existingPosition = state.get(POSITION);
        return (existingPosition & placePosition) == 0;
    }
    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(POSITION) != 0b11111111) {
            return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
        }
        return false;
    }
    @Override
    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        if (state.get(POSITION) != 0b11111111) {
            return Waterloggable.super.canFillWithFluid(player, world, pos, state, fluid);
        }
        return false;
    }
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return switch (type) {
            case LAND, AIR -> false;
            case WATER -> world.getFluidState(pos).isIn(FluidTags.WATER);
        };
    }
    
    //Boilerplate
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world,
                                                BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (state.isOf(state.getBlock())) {
            return;
        }
        world.updateNeighbor(this.BASE_BLOCK_STATE, pos, Blocks.AIR, pos, false);
        this.BASE_BLOCK.onBlockAdded(this.BASE_BLOCK_STATE, world, pos, oldState, false);
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        this.BASE_BLOCK_STATE.onStateReplaced(world, pos, newState, moved);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return this.BASE_BLOCK_STATE.onUse(world, player, hand, hit);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.BASE_BLOCK.randomTick(state, world, pos, random);
    }
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.BASE_BLOCK.scheduledTick(state, world, pos, random);
    }
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        this.BASE_BLOCK_STATE.onBlockBreakStart(world, pos, player);
    }
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return this.BASE_BLOCK.hasRandomTicks(state);
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.BASE_BLOCK.randomDisplayTick(state, world, pos, random);
    }
    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        this.BASE_BLOCK.onBroken(world, pos, state);
    }
    @Override
    public float getBlastResistance() {
        return this.BASE_BLOCK.getBlastResistance();
    }
    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        this.BASE_BLOCK.onDestroyedByExplosion(world, pos, explosion);
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        this.BASE_BLOCK.onSteppedOn(world, pos, state, entity);
    }
}
