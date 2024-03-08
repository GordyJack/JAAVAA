package net.gordyjack.jaavaa.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class JAAVAAWall
extends WallBlock {
    public JAAVAAWall(Settings settings) {
        super(settings);
    }
    
    public static class Translucent extends JAAVAAWall {
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
            if (stateFrom.isOf(this)) {
                return true;
            }
            return super.isSideInvisible(state, stateFrom, direction);
        }
    }
    public static class Transparent extends JAAVAAWall.Translucent {
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
            return true;
        }
    }
    public static class StainedGlass extends JAAVAAWall.Transparent implements Stainable {
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
    public static class TintedGlass extends JAAVAAWall.Translucent {
        public TintedGlass(Settings settings) {
            super(settings);
        }
        
        @Override
        public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
            return false;
        }
        @Override
        public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
            return world.getMaxLightLevel();
        }
    }
    public static class Redstone extends JAAVAAWall {
        public Redstone(Settings settings) {
            super(settings);
        }
        
        @Override
        public boolean emitsRedstonePower(BlockState state) {
            return true;
        }
        @Override
        public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
            return state.isSideSolidFullSquare(world, pos, direction.getOpposite()) ? 15 : 0;
        }
    }
}
