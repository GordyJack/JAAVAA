package net.gordyjack.jaavaa.item.custom.collectors;

import net.gordyjack.jaavaa.JAAVAA;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.*;

/**
 * A magnet item that can be enabled and disabled by right-clicking while sneaking.
 * When enabled, items surrounding the player will be attracted to the player at the movement speed of an AllayEntity.
 * When disabled, items will not be attracted to the player.
 * Some code is inspired by ZeroNoRyouki on Discord.
 */
public abstract class AbstractCollectorItem
extends AliasedBlockItem {
    //Fields
    private static final String DATA_KEY = JAAVAA.MODID + "_magnetstate";

    //Constructor
    public AbstractCollectorItem(Block block, Settings settings) {
        super(block, settings);
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
        if (entity instanceof PlayerEntity player && isActive(stack)) {
            this.pickupItems(world, player);
        } else {
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }
    abstract void pickupItems(World world, PlayerEntity entity);
    abstract float getPickupRange();
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
            user.sendMessage(Text.of("§eCollector " + (isActive(stack) ? "§2Enabled" : "§4Disabled")), true);
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
    protected List<ItemEntity> getInputItemEntities(World world, BlockPos pos) {
        VoxelShape shape = VoxelShapes.fullCube();
        return shape.getBoundingBoxes().stream().flatMap(
                box -> world.getEntitiesByClass(ItemEntity.class,
                        box.offset(pos.getX(), pos.getY(), pos.getZ()).expand(this.getPickupRange()),
                        EntityPredicates.VALID_ENTITY).stream()).collect(Collectors.toList());
    }
}
