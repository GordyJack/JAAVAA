package net.gordyjack.jaavaa.item.custom;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class AllayCollectorItem
        extends AbstractCollectorItem {
    private static final float PICKUP_RADIUS = 8.0f;
    public AllayCollectorItem(Block block, Settings settings) {
        super(block, settings);
    }
    @Override
    protected void pickupItems(World world, PlayerEntity player) {
        if (!player.isSneaking()) {
            for (ItemEntity itemEntity : this.getInputItemEntities(world, player.getBlockPos())) {
                if (itemEntity.isAlive() && !itemEntity.cannotPickup()) {
                    final float velocityScale = 40.0f;
                    itemEntity.addVelocity(
                            (player.getX() - itemEntity.getX()) / velocityScale,
                            (player.getY() - itemEntity.getY()) / velocityScale,
                            (player.getZ() - itemEntity.getZ()) / velocityScale);
                }
            }
            world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), player.getBoundingBox().expand(PICKUP_RADIUS), itemEntity -> {
                if (itemEntity.isAlive() && !itemEntity.cannotPickup()) {
                    final float velocityScale = 40.0f;
                    itemEntity.addVelocity(
                            (player.getX() - itemEntity.getX()) / velocityScale,
                            (player.getY() - itemEntity.getY()) / velocityScale,
                            (player.getZ() - itemEntity.getZ()) / velocityScale);
                }
                return false;
            });
        }
    }
    @Override
    float getPickupRange() {
        return PICKUP_RADIUS;
    }
}
