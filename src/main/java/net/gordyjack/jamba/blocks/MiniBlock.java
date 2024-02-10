package net.gordyjack.jamba.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class MiniBlock
extends Block
implements Waterloggable {
    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 8, 8);
    public static final VoxelShape DOWN_NORTH_WEST = BASE_SHAPE;
    public MiniBlock(Settings settings) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return DOWN_NORTH_WEST;
    }
}
