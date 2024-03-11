package net.gordyjack.jaavaa.item.custom;

import net.gordyjack.jaavaa.JAAVAA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * A magnet item that can be enabled and disabled by right-clicking while sneaking.
 * When enabled, items surrounding the player will be attracted to the player at the movement speed of an AllayEntity.
 * When disabled, items will not be attracted to the player.
 *
 * Some code is inspired by ZeroNoRyouki on Discord.
 */
public class CollectorItem
extends Item {
    //Fields
    private static final String DATA_KEY = JAAVAA.MODID + "_magnetstate";

    //Constructor
    public CollectorItem(Settings settings) {
        super(settings);
    }

    //Inherited Methods
    @Override
    public boolean hasGlint(ItemStack stack) {
        return isActive(stack);
    }
    /**
     * When the player has the collector in their inventory, the collector will attract items to the player if it is enabled.
     * @param stack The collector item
     * @param world The world the player is in
     * @param entity The player holding the collector
     * @param slot The slot the collector is in
     * @param selected Whether the collector is selected
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            super.inventoryTick(stack, world, entity, slot, selected);
            return;
        }
        if (isActive(stack) && !entity.isSneaking()) {
            world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), entity.getBoundingBox().expand(8.0, 8.0, 8.0), itemEntity -> {
                if (itemEntity.isAlive() && !itemEntity.cannotPickup()) {
                    itemEntity.setVelocity(
                            (entity.getX() - itemEntity.getX()) / 10.0,
                            (entity.getY() - itemEntity.getY()) / 10.0,
                            (entity.getZ() - itemEntity.getZ()) / 10.0
                    );
                }
                return false;
            });
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
    /**
     * When the player right-clicks with the collector in their main hand while sneaking, the collector will be enabled or disabled.
     * @param world The world the player is in
     * @param user The player using the collector
     * @param hand The hand the player used to right-click
     * @return The result of the action
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND && user.isSneaking()) {
            final ItemStack stack = user.getStackInHand(hand);
            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 2.0f);
            toggleActive(stack);
            user.sendMessage(Text.of("Collector " + (isActive(stack) ? "Enabled" : "Disabled")), true);
            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }

    //Helper Methods
    /**
     * Returns whether the collector is active.
     * @param stack The collector item
     * @return Whether the collector is active
     */
    private static boolean isActive(ItemStack stack) {
        final NbtCompound nbt = stack.getNbt();
        return null != nbt && nbt.contains(DATA_KEY) && nbt.getBoolean(DATA_KEY);
    }
    /**
     * Sets the active state of the collector.
     * @param stack The collector item
     * @param active Whether the collector is active
     */
    private static void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateNbt().putBoolean(DATA_KEY, active);
    }
    /**
     * Toggles the active state of the collector.
     * @param stack The collector item
     */
    private static void toggleActive(ItemStack stack) {
        final boolean currentState = isActive(stack);
        setActive(stack, !currentState);
    }
    
    public static class Empty extends CollectorItem {
        private final CollectorItem FILLED_ITEM;
        public Empty(Settings settings, CollectorItem filledItem) {
            super(settings);
            this.FILLED_ITEM = filledItem;
        }
        
        /**
         * When the player right-clicks with the empty collector, a message will be sent to the player informing them that the item is empty.
         * @param world The world the player is in
         * @param user The player using the collector
         * @param hand The hand the player used to right-click
         * @return The result of the action
         */
        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            if (!world.isClient()) {
                user.sendMessage(Text.of("This item is empty"), true);
            }
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        /**
         * When the player right-clicks on a capturable entity with the empty collector, the entity will be removed and the player will receive a filled collector.
         * @param stack The empty collector
         * @param user The player using the collector
         * @param entity The entity the player right-clicked on
         * @param hand The hand the player used to right-click
         * @return The result of the action
         */
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (!user.getWorld().isClient() && hand == Hand.MAIN_HAND && entity.getClass() == AllayEntity.class) {
                user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 2.0f);
                entity.remove(Entity.RemovalReason.DISCARDED);
                
                final ItemStack filledStack = new ItemStack(FILLED_ITEM);
                return this.tryExchangeStack(stack, user, filledStack, hand) ? ActionResult.SUCCESS : ActionResult.FAIL;
            }
            return super.useOnEntity(stack, user, entity, hand);
        }
        /**
         * Tries to exchange the old stack with the new stack. If the old stack has a count of 1, the new stack will be set in the user's hand. If the old stack has a count greater than 1, the old stack will be decremented by 1 and the new stack will be inserted into the user's inventory. If the user's inventory is full, the new stack will be dropped.
         * @param oldStack The old stack
         * @param user The player using the stack
         * @param newStack The new stack
         * @param hand The hand the player used to right-click
         * @return Whether the exchange was successful
         */
        private boolean tryExchangeStack(ItemStack oldStack, PlayerEntity user, ItemStack newStack, Hand hand) {
            if (oldStack.getCount() == 1) {
                user.setStackInHand(hand, newStack);
                return true;
            } else {
                oldStack.decrement(1);
                if (!user.getInventory().insertStack(newStack)) {
                    user.dropItem(newStack, false);
                    return true;
                }
            }
            return false;
        }
    }
}
