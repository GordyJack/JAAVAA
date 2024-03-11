package net.gordyjack.jaavaa.block.custom.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class AllayCollectorEntity
extends BlockEntity {
    //TODO: All of this
    public AllayCollectorEntity(BlockPos pos, BlockState state) {
        super(JAAVAABlockEntityTypes.ALLAY_COLLECTOR, pos, state);
    }
}
