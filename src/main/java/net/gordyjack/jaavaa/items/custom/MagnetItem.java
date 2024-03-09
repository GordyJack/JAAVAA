package net.gordyjack.jaavaa.items.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MagnetItem
extends Item {
    public MagnetItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(Hand.MAIN_HAND);
        if (hand == Hand.MAIN_HAND && user.isSneaking() && canEnable(stack)) {
            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5f, 0.9f);
            writeNbt(stack, "enabled", !isEnabled(stack));
            return TypedActionResult.consume(stack);
        }
        return super.use(world, user, hand);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(Hand.MAIN_HAND);
        if (hand == Hand.MAIN_HAND && entity instanceof AllayEntity && !canEnable(stackInHand)) {
            writeNbt(stackInHand, "canEnable", true);
            user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 2.0f);
            entity.remove(Entity.RemovalReason.DISCARDED);

            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return isEnabled(stack);
    }

    private boolean isEnabled(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean("enabled");
    }
    private boolean canEnable(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean("canEnable");
    }

    private static void writeNbt(ItemStack stack, String key, boolean value) {
        NbtCompound nbt = stack.hasNbt() ? stack.getNbt().copy() : new NbtCompound();
        nbt.putBoolean(key, value);
        stack.setNbt(nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        //TODO Items surrounding the player will be attracted to the player if the magnet is enabled at the movement speed of an AllayEntity
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
