package net.gordyjack.jamba.blocks;

import net.gordyjack.jamba.blocks.enums.*;
import net.gordyjack.jamba.blocks.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
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
    private static final EnumProperty<BlockSection> SECTION = ModBlockProperties.SECTION;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final Block BASE_BLOCK;
    private final BlockState BASE_BLOCK_STATE;
    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 8, 8);
    public final VoxelShape DOWN_NORTH_WEST = BASE_SHAPE;
    public final VoxelShape DOWN_NORTH_EAST = rotateShape(BASE_SHAPE, Direction.EAST);
    public final VoxelShape DOWN_SOUTH_WEST = rotateShape(BASE_SHAPE, Direction.WEST);
    public final VoxelShape DOWN_SOUTH_EAST = rotateShape(BASE_SHAPE, Direction.SOUTH);
    public final VoxelShape UP_NORTH_WEST = rotateShape(BASE_SHAPE, Direction.UP);
    public final VoxelShape UP_NORTH_EAST = rotateShape(UP_NORTH_WEST, Direction.EAST);
    public final VoxelShape UP_SOUTH_WEST = rotateShape(UP_NORTH_WEST, Direction.WEST);
    public final VoxelShape UP_SOUTH_EAST = rotateShape(UP_NORTH_WEST, Direction.SOUTH);
    
    public MiniBlock(Block baseBlock, Settings settings) {
        super(settings);
        this.BASE_BLOCK = baseBlock;
        this.BASE_BLOCK_STATE = this.BASE_BLOCK.getDefaultState();
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(SECTION)) {
            case DOWN_NORTH_WEST -> DOWN_NORTH_WEST;
            case DOWN_NORTH_EAST -> DOWN_NORTH_EAST;
            case DOWN_SOUTH_WEST -> DOWN_SOUTH_WEST;
            case DOWN_SOUTH_EAST -> DOWN_SOUTH_EAST;
            case UP_NORTH_WEST -> UP_NORTH_WEST;
            case UP_NORTH_EAST -> UP_NORTH_EAST;
            case UP_SOUTH_WEST -> UP_SOUTH_WEST;
            case UP_SOUTH_EAST -> UP_SOUTH_EAST;
        };
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        
        Vec3d hitPos = ctx.getHitPos();
        double xHitPos = hitPos.x;
        double yHitPos = hitPos.y;
        double zHitPos = hitPos.z;
        
        double xBlockPos = blockPos.getX();
        double yBlockPos = blockPos.getY();
        double zBlockPos = blockPos.getZ();
        
        double relativeXPos = xHitPos - xBlockPos;
        double relativeYPos = yHitPos - yBlockPos;
        double relativeZPos = zHitPos - zBlockPos;
        
        boolean north = relativeZPos <= .5;
        boolean south = relativeZPos > .5;
        boolean down = relativeYPos <=.5;
        boolean up = relativeYPos > .5;
        boolean west = relativeXPos <= .5;
        boolean east = relativeXPos > .5;
        
        if (down) {
            if (north) {
                if (west) {
                    blockState = blockState.with(SECTION, BlockSection.DOWN_NORTH_WEST);
                } else {
                    blockState = blockState.with(SECTION, BlockSection.DOWN_NORTH_EAST);
                }
            } else if (south) {
                if (west) {
                    blockState = blockState.with(SECTION, BlockSection.DOWN_SOUTH_WEST);
                } else {
                    blockState = blockState.with(SECTION, BlockSection.DOWN_SOUTH_EAST);
                }
            }
        } else if (up) {
            if (north) {
                if (west) {
                    blockState = blockState.with(SECTION, BlockSection.UP_NORTH_WEST);
                } else {
                    blockState = blockState.with(SECTION, BlockSection.UP_NORTH_EAST);
                }
            } else if (south) {
                if (west) {
                    blockState = blockState.with(SECTION, BlockSection.UP_SOUTH_WEST);
                } else {
                    blockState = blockState.with(SECTION, BlockSection.UP_SOUTH_EAST);
                }
            }
        }
        
        return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SECTION, WATERLOGGED);
    }
    
    //Boilerplate
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
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
