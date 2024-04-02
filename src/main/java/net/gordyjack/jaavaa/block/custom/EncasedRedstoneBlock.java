package net.gordyjack.jaavaa.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@SuppressWarnings("deprecation")
public class EncasedRedstoneBlock
        extends PillarBlock {
    public EncasedRedstoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(AXIS) == direction.getAxis() ? 15 : 0;
    }
}
