package net.gordyjack.jaavaa.block.custom.entity;

import net.gordyjack.jaavaa.block.custom.CollectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderCollectorEntity
        extends AbstractCollectorEntity {
    //Constants
    private static final float PICKUP_RANGE = 16.0f;
    private static final int PICKUP_DELAY = 2;
    private int ticksSinceLastPickup = 0;
    private ItemStack filter = ItemStack.EMPTY;

    //Constructor
    public EnderCollectorEntity(BlockPos pos, BlockState state) {
        super(JAAVAABlockEntityTypes.ENDER_COLLECTOR, pos, state);
    }

    //Inherrited Methods
    @Override
    public ItemStack getFilter() {
        return filter;
    }
    @Override
    public void setFilter(ItemStack stack) {
        this.filter = stack;
    }
    @Override
    public float getPickupRange() {
        return PICKUP_RANGE;
    }

    //Logic
    public static void tick(World world, BlockPos pos, BlockState state, EnderCollectorEntity collectorEntity) {
        if (world == null || world.isClient()) return;
        Inventory outputInventory;
        if (state.get(CollectorBlock.ENABLED) && (outputInventory = getOutputInventory(world, pos, state)) != null) {
            for (ItemEntity itemEntity : collectorEntity.getInputItemEntities(world, pos)) {
                if (itemEntity.isAlive()) {
                    ItemStack currentInput = itemEntity.getStack().copy();
                    itemEntity.resetPickupDelay();
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
}
