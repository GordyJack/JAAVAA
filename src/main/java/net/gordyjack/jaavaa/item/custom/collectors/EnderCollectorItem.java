package net.gordyjack.jaavaa.item.custom.collectors;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class EnderCollectorItem
        extends AbstractCollectorItem {
    public EnderCollectorItem(Block block, Settings settings) {
        super(block, settings);
    }
    //TODO: EnderCollectorItem pickup logic overrides EnderCollectorEntity pickup logic. This is not desired behaviour and is not present
    // in the Allay versions.
    @Override
    protected void pickupItems(World world, PlayerEntity player) {
        if (!player.isSneaking()) {
            for (ItemEntity itemEntity : this.getInputItemEntities(world, player.getBlockPos())) {
                if (itemEntity.isAlive() && !itemEntity.cannotPickup()) {
                    PlayerInventory inventory = player.getInventory();
                    if (inventory.insertStack(itemEntity.getStack())) {
                        inventory.markDirty();
                    }
                }
            }
        }
    }
    @Override
    protected float getPickupRange() {
        return 16.0f;
    }
}
