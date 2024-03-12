package net.gordyjack.jaavaa.block.custom.entity;

import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.block.custom.AllayCollectorBlock;
import net.gordyjack.jaavaa.mixinterfaces.ItemEntityMixinInterface;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Clearable;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllayCollectorEntity
extends BlockEntity
implements Clearable {
    private static final float ATTRACT_RANGE = 8.0f;
    private static final float PICKUP_RANGE = 0.001f;
    private ItemStack filter = ItemStack.EMPTY;

    //Constructor
    public AllayCollectorEntity(BlockPos pos, BlockState state) {
        super(JAAVAABlockEntityTypes.ALLAY_COLLECTOR, pos, state);
    }

    //Inherrited Methods
    @Override
    public void clear() {
        this.filter = ItemStack.EMPTY;
    }
    public ItemStack getStack() {
        return filter;
    }
    public void setStack(ItemStack stack) {
        this.filter = stack;
    }
    //NBT
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }
    //Logic
    public static void tick(World world, BlockPos pos, BlockState state, AllayCollectorEntity blockEntity) {
        if (world == null || world.isClient()) {
            return;
        }
        if (state.get(AllayCollectorBlock.ENABLED)) {
            attractItems(world, pos, blockEntity);
            Inventory inventory;
            if ((inventory = getOutputInventory(world, pos, state)) != null)
                pickupItems(world, pos, inventory, state, blockEntity);
        }
    }
    private static void attractItems(World world, BlockPos pos, AllayCollectorEntity blockEntity) {
        world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), Box.from(Vec3d.of(pos)).expand(ATTRACT_RANGE), itemEntity -> {
            if (itemEntity.isAlive() && blockEntity.doesFilterMatch(itemEntity.getStack())) {
                if (itemEntity.getPos() != ((ItemEntityMixinInterface)itemEntity).jaavaa$getOldPos()
                        && !((ItemEntityMixinInterface)itemEntity).jaavaa$delayEnded()) {
                    JAAVAA.logError("SETTING");
                    itemEntity.setPickupDelay(200);
                    itemEntity.setNoGravity(true);
                    ((ItemEntityMixinInterface)itemEntity).jaavaa$setOldPos(itemEntity.getPos());
                }
                if (!itemEntity.cannotPickup()) {
                    JAAVAA.logError("CAN'T PICKUP");
                    ((ItemEntityMixinInterface)itemEntity).jaavaa$setDelayEnded(true);
                    itemEntity.setNoGravity(false);
                } else {
                    JAAVAA.logError("ATTRACTING");
                    if (getInputItemEntities(world, pos).contains(itemEntity)) {
                        itemEntity.setVelocity(Vec3d.ZERO);
                    } else {
                        final double VEL_S = 15;
                        final double MAX_V = .1;
                        double xDistance = pos.getX() + 0.5f - itemEntity.getX();
                        double yDistance = pos.getY() - itemEntity.getY();
                        double zDistance = pos.getZ() + 0.5f - itemEntity.getZ();
                        double xVelocity = xDistance / VEL_S;
                        double yVelocity = yDistance / VEL_S;
                        double zVelocity = zDistance / VEL_S;
                        itemEntity.setVelocity(xVelocity, yVelocity, zVelocity);
                    }
                }
            }
            return false;
        });
    }
    private static void pickupItems(World world, BlockPos pos, Inventory inventory, BlockState state, AllayCollectorEntity blockEntity) {
        for (ItemEntity itemEntity : getInputItemEntities(world, pos)) {
            if (itemEntity.isAlive()) {
                ItemStack currentInput = itemEntity.getStack().copy();
                if (currentInput.isEmpty() || inventory == null
                        || isInventoryFull(inventory, state.get(AllayCollectorBlock.FACING).getOpposite())
                        || !blockEntity.doesFilterMatch(currentInput)) {
                    return;
                } else {
                    if (insertStack(inventory, currentInput, state.get(AllayCollectorBlock.FACING).getOpposite())) {
                        if (currentInput.isEmpty()) {
                            itemEntity.discard();
                        } else {
                            itemEntity.setStack(currentInput);
                        }
                        inventory.markDirty();
                    }
                }
            }
        }
    }
    //Helper Methods
    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        }
        return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
    }
    private static boolean canMergeItems(ItemStack initialStack, ItemStack addedStack) {
        return initialStack.getCount() < initialStack.getMaxCount() && ItemStack.canCombine(initialStack, addedStack);
    }
    private boolean doesFilterMatch(ItemStack inputStack) {
        return this.filter.isEmpty() || ItemStack.areItemsEqual(this.filter, inputStack);
    }
    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory)inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }
    public static List<ItemEntity> getInputItemEntities(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        VoxelShape shape = state.getOutlineShape(world, pos);
        return shape.getBoundingBoxes().stream().flatMap(
                box -> world.getEntitiesByClass(ItemEntity.class, box.offset(pos.getX(), pos.getY(), pos.getZ()).expand(PICKUP_RANGE), EntityPredicates.VALID_ENTITY).stream()
        ).collect(Collectors.toList());
    }
    @Nullable
    private static Inventory getOutputInventory(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(AllayCollectorBlock.FACING);
        return HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }
    public static boolean insertStack(Inventory outputInventory, ItemStack stack, @Nullable Direction side) {
        if (outputInventory instanceof SidedInventory) {
            SidedInventory sidedInventory = (SidedInventory) outputInventory;
            if (side != null) {
                int[] is = sidedInventory.getAvailableSlots(side);
                int i = 0;
                while (i < is.length) {
                    if (AllayCollectorEntity.insertStack(outputInventory, stack, is[i], side)) return true;
                    ++i;
                }
                return false;
            }
        }
        int j = outputInventory.size();
        int i = 0;
        while (i < j) {
            if (AllayCollectorEntity.insertStack(outputInventory, stack, i, side)) return true;
            ++i;
        }
        return false;
    }
    private static boolean insertStack(Inventory outputInventory, ItemStack stack, int slot, @Nullable Direction side) {
        ItemStack itemStack = outputInventory.getStack(slot);
        if (AllayCollectorEntity.canInsert(outputInventory, stack, slot, side)) {
            if (itemStack.isEmpty()) {
                outputInventory.setStack(slot, stack.copy());
                stack.decrement(stack.getCount());
                return true;
            } else if (AllayCollectorEntity.canMergeItems(itemStack, stack)) {
                int spaceInStack = stack.getMaxCount() - itemStack.getCount();
                int itemCount = Math.min(stack.getCount(), spaceInStack);
                stack.decrement(itemCount);
                itemStack.increment(itemCount);
                return true;
            }
        }
        return false;
    }
    private static boolean isInventoryFull(Inventory inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch(slot -> {
            ItemStack itemStack = inventory.getStack(slot);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }
}
