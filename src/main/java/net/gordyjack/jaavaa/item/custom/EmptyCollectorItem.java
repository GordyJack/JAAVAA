package net.gordyjack.jaavaa.item.custom;

import net.gordyjack.jaavaa.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class EmptyCollectorItem extends Item {
    public EmptyCollectorItem(Item.Settings settings) {
        super(settings);
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
        if (!user.getWorld().isClient() && hand == Hand.MAIN_HAND && (entity instanceof AllayEntity || entity instanceof EndermanEntity)) {
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 2.0f);
            entity.remove(Entity.RemovalReason.DISCARDED);
            
            ItemStack filledStack;
            if (entity instanceof AllayEntity) {
                filledStack = new ItemStack(JAAVAAItems.PERSONAL_ALLAY_COLLECTOR);
            } else {
                filledStack = new ItemStack(JAAVAAItems.PERSONAL_ENDER_COLLECTOR);
            }
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
