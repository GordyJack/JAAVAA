package net.gordyjack.jaavaa.items.custom;

import net.gordyjack.jaavaa.JAAVAA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
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
public class MagnetItem
extends Item {
    //Fields
    private static final String DATA_KEY = JAAVAA.MODID + "_magnetstate";
    private static final byte STATE_NOT_CAPTURED = 0;
    private static final byte STATE_ACTIVE = 1;
    private static final byte STATE_INACTIVE = 2;

    //Constructor
    public MagnetItem(Settings settings) {
        super(settings);
    }

    //Inherited Methods
    /**
     * When the player right-clicks with the magnet in their main hand while sneaking, the magnet will be enabled or disabled.
     * @param world The world the player is in
     * @param user The player using the magnet
     * @param hand The hand the player used to right-click
     * @return The result of the action
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND && user.isSneaking()) {
            final ItemStack stack = user.getStackInHand(hand);
            if (toggleItemState(stack)) {
                return TypedActionResult.success(stack);
            } else {
                user.sendMessage(Text.literal("You need to capture an Allay first!"), true);
            }
        }
        return super.use(world, user, hand);
    }

    /**
     * When the player right-clicks on an AllayEntity with the magnet in their main hand,
     * if the magnet is not captured, the AllayEntity will be removed and the magnet will be enabled.
     * @param stack The magnet item
     * @param user The player using the magnet
     * @param entity The entity the player right-clicked on
     * @param hand The hand the player used to right-click
     * @return The result of the action
     */
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient() && hand == Hand.MAIN_HAND && entity instanceof AllayEntity && getItemState(stack) == STATE_NOT_CAPTURED) {
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 2.0f);
            entity.remove(Entity.RemovalReason.DISCARDED);
            changeItemState(stack, STATE_NOT_CAPTURED, STATE_ACTIVE);

            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return getItemState(stack) == STATE_ACTIVE;
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            super.inventoryTick(stack, world, entity, slot, selected);
            return;
        }
        if (getItemState(stack) == STATE_ACTIVE) {
            world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), entity.getBoundingBox().expand(5.0, 5.0, 5.0), itemEntity -> {
                if (itemEntity.isAlive() ) {
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

    //Helper Methods
    private static boolean changeItemState(ItemStack stack, byte currentItemState, byte newItemState) {
        final byte validNewState = getNextValidItemState(currentItemState);
        if (STATE_NOT_CAPTURED != validNewState && validNewState == newItemState) {
            stack.getOrCreateNbt().putByte(DATA_KEY, validNewState);
            return true;
        }
        return false;
    }
    private static byte getNextValidItemState(byte currentItemState) {
        return switch (currentItemState) {
            case STATE_NOT_CAPTURED, STATE_INACTIVE -> STATE_ACTIVE;
            case STATE_ACTIVE -> STATE_INACTIVE;
            default -> STATE_NOT_CAPTURED;
        };
    }
    private static byte getItemState(ItemStack stack) {
        final NbtCompound nbt = stack.getNbt();
        if (null != nbt && nbt.contains(DATA_KEY, NbtElement.BYTE_TYPE)) {
            final byte stateValue = nbt.getByte(DATA_KEY);
            return switch (stateValue) {
                case STATE_ACTIVE, STATE_INACTIVE -> stateValue;
                default -> STATE_NOT_CAPTURED;
            };
        }
        return STATE_NOT_CAPTURED;
    }
    private static boolean toggleItemState(ItemStack stack) {
        final byte currentState = getItemState(stack);
        return STATE_NOT_CAPTURED != currentState && changeItemState(stack, currentState, getNextValidItemState(currentState));
    }
}
