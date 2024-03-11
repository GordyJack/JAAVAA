package net.gordyjack.jaavaa.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

        //I'm still unsure if this is the best way to do this. But it works in a way that at least makes some sense.
        @Override
        public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
            return state.get(TYPE) == SlabType.DOUBLE ? world.getMaxLightLevel() : super.getOpacity(state, world, pos);
        }
        @Override
        public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
            return state.get(TYPE) == SlabType.DOUBLE;
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
    public static class Slime extends JAAVAASlab.Translucent {
        public Slime(Settings settings) {
            super(settings);
        }

        @Override
        public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
            if (entity.bypassesLandingEffects()) {
                super.onLandedUpon(world, state, pos, entity, fallDistance);
            } else {
                entity.handleFallDamage(fallDistance, 0.0f, world.getDamageSources().fall());
            }
        }
        @Override
        public void onEntityLand(BlockView world, Entity entity) {
            if (entity.bypassesLandingEffects()) {
                super.onEntityLand(world, entity);
            } else {
                this.bounce(entity);
            }
        }
        private void bounce(Entity entity) {
            Vec3d vec3d = entity.getVelocity();
            if (vec3d.y < 0.0) {
                double d = entity instanceof LivingEntity ? 1.0 : 0.8;
                entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
            }
        }
        @Override
        public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
            double d = Math.abs(entity.getVelocity().y);
            if (d < 0.1 && !entity.bypassesSteppingEffects()) {
                double e = 0.4 + d * 0.2;
                entity.setVelocity(entity.getVelocity().multiply(e, 1.0, e));
            }
            super.onSteppedOn(world, pos, state, entity);
        }
    }
}
