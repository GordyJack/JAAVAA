package net.gordyjack.jaavaa.items.custom;

import net.gordyjack.jaavaa.JAAVAA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
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
public class MagnetItem
extends Item {
    //Fields
    private static final String DATA_KEY = JAAVAA.MODID + "_magnetstate";

    //Constructor
    public MagnetItem(Settings settings) {
        super(settings);
    }

    //Inherited Methods
    @Override
    public boolean hasGlint(ItemStack stack) {
        return isActive(stack);
    }
    /**
     * When the player has the magnet in their inventory, the magnet will attract items to the player if it is enabled.
     * @param stack The magnet item
     * @param world The world the player is in
     * @param entity The player holding the magnet
     * @param slot The slot the magnet is in
     * @param selected Whether the magnet is selected
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            super.inventoryTick(stack, world, entity, slot, selected);
            return;
        }
        if (isActive(stack)) {
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
            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 2.0f);
            toggleActive(stack);
            user.sendMessage(Text.of("Magnet " + (isActive(stack) ? "disabled" : "enabled")), true);
            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }

    //Helper Methods
    private static boolean isActive(ItemStack stack) {
        final NbtCompound nbt = stack.getNbt();
        return null != nbt && nbt.contains(DATA_KEY) && nbt.getBoolean(DATA_KEY);
    }
    private static void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateNbt().putBoolean(DATA_KEY, active);
    }
    private static void toggleActive(ItemStack stack) {
        final boolean currentState = isActive(stack);
        setActive(stack, !currentState);
    }
    public static class Empty extends MagnetItem {
        private final Class<? extends Entity> CAPTURABLE_ENTITY;
        private final Item FILLED_ITEM;
        public Empty(Settings settings, Class<? extends Entity> capturableEntity, Item filledItem) {
            super(settings);

            this.CAPTURABLE_ENTITY = capturableEntity;
            this.FILLED_ITEM = filledItem;
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            if (!world.isClient()) {
                user.sendMessage(Text.of("This item is empty"), true);
            }
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        //TODO: FIX: filledStack is air if stack has count 1
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (!user.getWorld().isClient() && hand == Hand.MAIN_HAND && entity.getClass() == CAPTURABLE_ENTITY) {
                user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 2.0f);
                entity.remove(Entity.RemovalReason.DISCARDED);

                final ItemStack filledStack = ItemUsage.exchangeStack(stack, user, new ItemStack(FILLED_ITEM), false);
                JAAVAA.logError("Filled stack: " + filledStack + ", Stack in Hand: " + user.getStackInHand(hand));
                //MagnetItem.setActive(filledStack, true);
                return ActionResult.SUCCESS;
            }
            return super.useOnEntity(stack, user, entity, hand);
        }
    }
}
