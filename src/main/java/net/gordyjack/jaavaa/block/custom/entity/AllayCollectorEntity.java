package net.gordyjack.jaavaa.block.custom.entity;

import net.gordyjack.jaavaa.block.custom.CollectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AllayCollectorEntity
extends AbstractCollectorEntity {
    private static final float ATTRACT_RANGE = 8.0f;
    private static final float PICKUP_RANGE = 0.001f;
    private static final int PICKUP_DELAY = 5;
    private int ticksSinceLastPickup = 0;
    private ItemStack filter = ItemStack.EMPTY;

    //Constructor
    public AllayCollectorEntity(BlockPos pos, BlockState state) {
        super(JAAVAABlockEntityTypes.ALLAY_COLLECTOR, pos, state);
    }

    //Inherrited Methods
    public ItemStack getFilter() {
        return filter;
    }
    public void setFilter(ItemStack stack) {
        this.filter = stack;
    }
    public float getPickupRange() {
        return PICKUP_RANGE;
    }
    //NBT
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }
    //Logic
    public static void tick(World world, BlockPos pos, BlockState state, AllayCollectorEntity collectorEntity) {
        if (world == null || world.isClient()) return;
        if (state.get(CollectorBlock.ENABLED)) {
            attractItems(world, pos, collectorEntity);
            Inventory outputInventory;
            if ((outputInventory = getOutputInventory(world, pos, state)) != null) {
                pickupItems(world, pos, outputInventory, state, collectorEntity);
            }
        }
    }
    private static void attractItems(World world, BlockPos pos, AllayCollectorEntity collectorEntity) {
        world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), Box.from(Vec3d.of(pos)).expand(ATTRACT_RANGE), itemEntity -> {
            if (itemEntity.isAlive() && collectorEntity.doesFilterMatch(itemEntity.getStack())) {
                if (itemEntity.getPos() != itemEntity.jaavaa$getOldPos()
                        && !itemEntity.jaavaa$delayEnded()) {
                    itemEntity.setPickupDelay(200);
                    itemEntity.setNoGravity(true);
                    itemEntity.jaavaa$setOldPos(itemEntity.getPos());
                }
                if (!itemEntity.cannotPickup()) {
                    itemEntity.jaavaa$setDelayEnded(true);
                    itemEntity.setNoGravity(false);
                } else {
                    if (collectorEntity.getInputItemEntities(world, pos).contains(itemEntity)) {
                        itemEntity.setVelocity(Vec3d.ZERO);
                    } else {
                        final double VEL_S = 15;
                        double xDistance = pos.getX() + 0.5f - itemEntity.getX();
                        double yDistance = pos.getY() - itemEntity.getY();
                        double zDistance = pos.getZ() + 0.5f - itemEntity.getZ();
                        double xVelocity = xDistance / VEL_S;
                        double yVelocity = yDistance / VEL_S;
                        double zVelocity = zDistance / VEL_S;
                        itemEntity.setVelocity(xVelocity, yVelocity, zVelocity);
                    }
                    itemEntity.velocityDirty = true;
                }
            }
            return false;
        });
    }
    private static void pickupItems(World world, BlockPos pos, Inventory outputInventory, BlockState state, AllayCollectorEntity collectorEntity) {
        for (ItemEntity itemEntity : collectorEntity.getInputItemEntities(world, pos)) {
            if (itemEntity.isAlive()) {
                ItemStack currentInput = itemEntity.getStack().copy();
                if (currentInput.isEmpty() || isInventoryFull(outputInventory, state.get(CollectorBlock.FACING).getOpposite())
                        || !collectorEntity.doesFilterMatch(currentInput) || collectorEntity.ticksSinceLastPickup < PICKUP_DELAY) {
                    collectorEntity.ticksSinceLastPickup++;
                    return;
                } else {
                    if (insertStack(outputInventory, currentInput, state.get(CollectorBlock.FACING).getOpposite())) {
                        if (currentInput.isEmpty()) {
                            itemEntity.discard();
                        } else {
                            itemEntity.setStack(currentInput);
                        }
                        outputInventory.markDirty();
                        collectorEntity.ticksSinceLastPickup = 0;
                    }
                }
            }
        }
    }
}
