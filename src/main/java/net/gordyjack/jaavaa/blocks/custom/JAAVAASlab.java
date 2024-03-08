package net.gordyjack.jaavaa.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

@SuppressWarnings("deprecation")
public class JAAVAASlab
extends SlabBlock {
    public JAAVAASlab(Settings settings) {
        super(settings);
    }
    
    public static class Translucent extends JAAVAASlab {
        public Translucent(Settings settings) {
            super(settings);
        }
        
        /**
         * Determines if a side of a block should be considered invisible.
         *
         * @param state The current block state.
         * @param stateFrom The block state from the given direction.
         * @param direction The direction from the current block to the block in question.
         * @return true if the side should be invisible, false otherwise.
         */
        @Override
        public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
            if (stateFrom.isOf(this)
                    && (sideIsSameType(state, stateFrom, direction)
                    || doesTopTouchBottom(state, stateFrom, direction))) {
                return true;
            }
            return super.isSideInvisible(state, stateFrom, direction);
        }
        /**
         * Determines if a top face is touching a bottom face.
         *
         * @param state The block state.
         * @param stateFrom The block state from the given direction.
         * @param direction The direction from the current block to the block in question.
         * @return true if the bottom and top faces of a block touch, false otherwise.
         */
        private boolean doesTopTouchBottom(BlockState state, BlockState stateFrom, Direction direction) {
            SlabType stateType = state.get(SlabBlock.TYPE);
            SlabType stateFromType = stateFrom.get(SlabBlock.TYPE);

            boolean isTopOrDouble = stateType == SlabType.TOP || stateType == SlabType.DOUBLE;
            boolean isBottomOrDouble = stateType == SlabType.BOTTOM || stateType == SlabType.DOUBLE;
            boolean fromIsTopOrDouble = stateFromType == SlabType.TOP || stateFromType == SlabType.DOUBLE;
            boolean fromIsBottomOrDouble = stateFromType == SlabType.BOTTOM || stateFromType == SlabType.DOUBLE;

            return ((isTopOrDouble && fromIsBottomOrDouble && direction == Direction.UP)
                    || (isBottomOrDouble && fromIsTopOrDouble && direction == Direction.DOWN));
        }
        private boolean sideIsSameType(BlockState state, BlockState stateFrom, Direction direction) {
            return direction.getAxis() != Direction.Axis.Y && state.get(SlabBlock.TYPE) == stateFrom.get(SlabBlock.TYPE);
        }
    }
    public static class Transparent extends Translucent {
        public Transparent(Settings settings) {
            super(settings);
        }
        
        @Override
        public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return VoxelShapes.empty();
        }
        @Override
        public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
            return 1.0f;
        }
        @Override
        public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
            return !state.get(WATERLOGGED) || this.hasSidedTransparency(state);
        }
    }
    public static class StainedGlass extends Transparent implements Stainable {
        private final DyeColor COLOR;
        public StainedGlass(DyeColor color, Settings settings) {
            super(settings);
            this.COLOR = color;
        }
        
        @Override
        public DyeColor getColor() {
            return this.COLOR;
        }
    }
    public static class TintedGlass extends Translucent {
        public TintedGlass(Settings settings) {
            super(settings);
        }
        
        @Override
        public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
            return world.getMaxLightLevel();
        }
        @Override
        public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
            return false;
        }
    }
    public static class Redstone extends JAAVAASlab {
        public Redstone(Settings settings) {
            super(settings);
        }
        
        @Override
        public boolean emitsRedstonePower(BlockState state) {
            return true;
        }
        @Override
        public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
            boolean isTop = state.get(SlabBlock.TYPE) == SlabType.TOP;
            boolean isBottom = state.get(SlabBlock.TYPE) == SlabType.BOTTOM;
            boolean isDouble = state.get(SlabBlock.TYPE) == SlabType.DOUBLE;
            return isDouble || (isTop && direction != Direction.UP) || (isBottom && direction != Direction.DOWN) ? 15 : 0;
        }
    }
}
