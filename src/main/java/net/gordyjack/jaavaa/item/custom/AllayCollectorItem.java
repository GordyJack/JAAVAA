package net.gordyjack.jaavaa.item.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

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
                    itemEntity.setVelocity(
                            ((player.getX() + 0.3f) - itemEntity.getX()) / velocityScale,
                            ((player.getY() + 0.9f) - itemEntity.getY()) / velocityScale,
                            ((player.getZ() + 0.3f) - itemEntity.getZ()) / velocityScale);
                    itemEntity.velocityDirty = true;
                }
            }
        }
    }
    @Override
    float getPickupRange() {
        return PICKUP_RADIUS;
    }
}
