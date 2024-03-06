package net.gordyjack.jaavaa.blocks.utils;

import net.minecraft.block.*;
import net.minecraft.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;

public interface VoxelShapeUtils {
    /**
     * Rotates the given VoxelShape around the specified axis to align it with the given direction.
     *
     * @param fromShape the shape to rotate
     * @param toDirection the direction to align the shape with after rotation
     * @return the rotated VoxelShape
     * @throws IllegalArgumentException if the toDirection is the same as the axis of rotation
     * @throws IllegalStateException if the axis is not a recognized value
     */
    default VoxelShape rotateShape(VoxelShape fromShape, Direction toDirection) {
        if (toDirection == Direction.UP || toDirection == Direction.DOWN) {
            return rotateShapeDownToNorth(fromShape);
        } else {
            VoxelShape[] buffer = new VoxelShape[]{fromShape, VoxelShapes.empty()};
            int times = calculateRotationTimes(toDirection);
            for (int i = 0; i < times; i++) {
                buffer = rotateOnce(buffer);
            }
            
            return buffer[0];
        }
    }
    
    /**
     * Rotates a VoxelShape 90 degrees around the Z-axis so that the DOWN face becomes the NORTH face, and the NORTH face becomes the UP face.
     *
     * @param fromShape The original VoxelShape to rotate.
     * @return The rotated VoxelShape.
     */
    private VoxelShape rotateShapeDownToNorth(VoxelShape fromShape) {
        // Buffer for intermediate and final VoxelShapes
        VoxelShape[] buffer = new VoxelShape[] {fromShape, VoxelShapes.empty()};
        
        // Rotate once around the Z-axis
        buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Apply transformation for 90-degree rotation around Z-axis
            double newMinY = 1 - maxZ;
            double newMaxY = 1 - minZ;
            double newMinZ = minY;
            double newMaxZ = maxY;
            // Create a new VoxelShape with the transformed coordinates and combine it with the buffer
            buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(minX, newMinY, newMinZ, maxX, newMaxY, newMaxZ));
        });
        
        // Return the rotated shape
        return buffer[1];
    }
    
    /**
     * Calculates the number of 90-degree rotations needed based on the direction.
     *
     * @param toDirection the direction to align the shape with after rotation
     * @return the number of times to rotate by 90 degrees
     */
    private int calculateRotationTimes(Direction toDirection) {
        return toDirection.getHorizontal() + 2 % 4;
    }
    
    /**
     * Performs a single 90-degree rotation on the buffer shapes.
     *
     * @param buffer the array of VoxelShapes where buffer[0] is the shape to rotate and buffer[1] is an empty shape
     * @return the array of VoxelShapes after rotation
     */
    private VoxelShape[] rotateOnce(VoxelShape[] buffer) {
        buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
        });
        
        // Prepare for the next iteration or final result.
        VoxelShape rotatedShape = buffer[1];
        buffer[1] = VoxelShapes.empty();
        
        // Return the updated buffer array.
        return new VoxelShape[]{rotatedShape, buffer[1]};
    }
    
    default VoxelShape flipShape(VoxelShape shape) {
        return flipShape(shape, Direction.Axis.Y);
    }
    default VoxelShape flipShape(VoxelShape shape, Direction.Axis axis) {
        VoxelShape flippedShape = VoxelShapes.empty();
        
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    double minX = x / 16.0;
                    double minY = y / 16.0;
                    double minZ = z / 16.0;
                    double maxX = (x + 1) / 16.0;
                    double maxY = (y + 1) / 16.0;
                    double maxZ = (z + 1) / 16.0;
                    
                    switch (axis) {
                        case X -> {
                            double temp = minX;
                            minX = 1 - maxX;
                            maxX = 1 - temp;
                        }
                        case Y -> {
                            double temp = minY;
                            minY = 1 - maxY;
                            maxY = 1 - temp;
                        }
                        case Z -> {
                            double temp = minZ;
                            minZ = 1 - maxZ;
                            maxZ = 1 - temp;
                        }
                    }
                    
                    if (VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ), BooleanBiFunction.AND)) {
                        flippedShape = VoxelShapes.union(flippedShape, Block.createCuboidShape(x, y, z, x + 1, y + 1, z + 1));
                    }
                }
            }
        }
        return flippedShape;
    }
    default VoxelShape mergeShapes(VoxelShape shape1, VoxelShape shape2) {
        return VoxelShapes.union(shape1, shape2);
    }
    default VoxelShape intersectShapes(VoxelShape shape1, VoxelShape shape2) {
        return VoxelShapes.combineAndSimplify(shape1, shape2, BooleanBiFunction.AND);
    }
    default VoxelShape translateShape(VoxelShape fromShape, Direction direction, int pixels) {
        // Get the bounding box of the original shape
        var box = fromShape.getBoundingBox();
        
        // Calculate the offset in each direction based on the pixels
        double offsetX = 0;
        double offsetY = 0;
        double offsetZ = 0;
        
        switch (direction) {
            case NORTH:
                offsetZ = -pixels;
                break;
            case SOUTH:
                offsetZ = pixels;
                break;
            case EAST:
                offsetX = pixels;
                break;
            case WEST:
                offsetX = -pixels;
                break;
            case UP:
                offsetY = pixels;
                break;
            case DOWN:
                offsetY = -pixels;
                break;
        }
        
        // Translate the shape by adjusting its bounding box with the offset
        return Block.createCuboidShape(
                box.minX*16 + offsetX, box.minY*16 + offsetY, box.minZ*16 + offsetZ,
                box.maxX*16 + offsetX, box.maxY*16 + offsetY, box.maxZ*16 + offsetZ);
    }
}
