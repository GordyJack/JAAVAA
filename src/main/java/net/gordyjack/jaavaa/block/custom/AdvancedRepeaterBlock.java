package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.MapCodec;
import net.gordyjack.jaavaa.block.JAAVAABlockProperties;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

//TODO: Will need to rewrite this class from the ground up extending block due to limitations in how  AbstractRedstoneGateBlock handles the update delay and pulse length
//Probably will need to use separate boolean properties for EMITTING_POWER and RECIEVING_POWER
@SuppressWarnings("deprecation")
public class AdvancedRepeaterBlock
        extends AbstractRedstoneGateBlock {
    public static final MapCodec<AdvancedRepeaterBlock> CODEC = AdvancedRepeaterBlock.createCodec(AdvancedRepeaterBlock::new);
    public static final IntProperty DELAY = JAAVAABlockProperties.DELAY;
    public static final IntProperty PULSE = JAAVAABlockProperties.PULSE;

    public AdvancedRepeaterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DELAY, 0).with(PULSE, 0).with(POWERED, false).with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends AbstractRedstoneGateBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return state.get(DELAY) * 2;
    }
    protected int getPulseLengthInternal(BlockState state) {
        return state.get(PULSE) * 2;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DELAY, PULSE, POWERED, FACING);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        }
        Direction facing = state.get(FACING);
        boolean adjustDelay;
        boolean adjustPulse;
        double xPos = hit.getPos().x - pos.getX();
        double zPos = hit.getPos().z - pos.getZ();
        switch (facing) {
            case NORTH -> {
                if (zPos <= 0.5) {
                    adjustDelay = true;
                    adjustPulse = false;
                } else {
                    adjustDelay = false;
                    adjustPulse = true;
                }
            }
            case SOUTH -> {
                if (zPos >= 0.5) {
                    adjustDelay = true;
                    adjustPulse = false;
                } else {
                    adjustDelay = false;
                    adjustPulse = true;
                }
            }
            case WEST -> {
                if (xPos >= 0.5) {
                    adjustDelay = true;
                    adjustPulse = false;
                } else {
                    adjustDelay = false;
                    adjustPulse = true;
                }
            }
            case EAST -> {
                if (xPos <= 0.5) {
                    adjustDelay = true;
                    adjustPulse = false;
                } else {
                    adjustDelay = false;
                    adjustPulse = true;
                }
            }
            default -> {
                return ActionResult.PASS;
            }
        }
        if (adjustDelay) {
            world.setBlockState(pos, state.cycle(DELAY), Block.NOTIFY_ALL);
        }
        if (adjustPulse) {
            world.setBlockState(pos, state.cycle(PULSE), Block.NOTIFY_ALL);
        }
        return ActionResult.success(world.isClient);
    }
    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (this.isLocked(world, pos, state)) {
            return;
        }
        boolean powered = state.get(POWERED);
        if (powered != this.hasPower(world, pos, state) && !world.getBlockTickScheduler().isTicking(pos, this)) {
            TickPriority tickPriority = TickPriority.HIGH;
            if (this.isTargetNotAligned(world, pos, state)) {
                tickPriority = TickPriority.EXTREMELY_HIGH;
            } else if (powered) {
                tickPriority = TickPriority.VERY_HIGH;
            }
            world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), tickPriority);
        }
    }
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isLocked(world, pos, state)) {
            return;
        }
        boolean powered = state.get(POWERED);
        boolean shouldBePowered = this.hasPower(world, pos, state);
        if (powered && !shouldBePowered) {
            world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
        } else if (!powered) {
            world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_LISTENERS);
            if (!shouldBePowered) {
                world.scheduleBlockTick(pos, this, this.getPulseLengthInternal(state), TickPriority.VERY_HIGH);
            }
        }
    }
}
