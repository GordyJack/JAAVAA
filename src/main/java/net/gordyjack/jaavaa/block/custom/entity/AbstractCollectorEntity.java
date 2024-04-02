package net.gordyjack.jaavaa.block.custom.entity;

import net.gordyjack.jaavaa.block.custom.CollectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractCollectorEntity extends BlockEntity implements Clearable {
    public AbstractCollectorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract ItemStack getFilter();
    public abstract void setFilter(ItemStack stack);
    public abstract float getPickupRange();

    @Override
    public void clear() {
        this.setFilter(ItemStack.EMPTY);
    }
    boolean doesFilterMatch(ItemStack inputStack) {
        return this.getFilter().isEmpty() || ItemStack.areItemsEqual(this.getFilter(), inputStack);
    }
    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        }
        return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
    }
    private static boolean canMergeItems(ItemStack initialStack, ItemStack addedStack) {
        return initialStack.getCount() < initialStack.getMaxCount() && ItemStack.canCombine(initialStack, addedStack);
    }
    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory)inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }
    public List<ItemEntity> getInputItemEntities(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        VoxelShape shape = state.getOutlineShape(world, pos);
        return shape.getBoundingBoxes().stream().flatMap(
                box -> world.getEntitiesByClass(ItemEntity.class,
                        box.offset(pos.getX(), pos.getY(), pos.getZ()).expand(this.getPickupRange()),
                        EntityPredicates.VALID_ENTITY).stream()).collect(Collectors.toList());
    }
    @Nullable
    static Inventory getOutputInventory(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(CollectorBlock.FACING);
        return HopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }
    public static boolean insertStack(Inventory outputInventory, ItemStack stack, @Nullable Direction side) {
        if (outputInventory instanceof SidedInventory sidedInventory) {
            if (side != null) {
                int[] is = sidedInventory.getAvailableSlots(side);
                int i = 0;
                while (i < is.length) {
                    if (AbstractCollectorEntity.insertStack(outputInventory, stack, is[i], side)) return true;
                    ++i;
                }
                return false;
            }
        }
        int j = outputInventory.size();
        int i = 0;
        while (i < j) {
            if (AbstractCollectorEntity.insertStack(outputInventory, stack, i, side)) return true;
            ++i;
        }
        return false;
    }
    private static boolean insertStack(Inventory outputInventory, ItemStack stack, int slot, @Nullable Direction side) {
        ItemStack itemStack = outputInventory.getStack(slot);
        if (AbstractCollectorEntity.canInsert(outputInventory, stack, slot, side)) {
            if (itemStack.isEmpty()) {
                outputInventory.setStack(slot, stack.copy());
                stack.decrement(stack.getCount());
                return true;
            } else if (AbstractCollectorEntity.canMergeItems(itemStack, stack)) {
                int spaceInStack = stack.getMaxCount() - itemStack.getCount();
                int itemCount = Math.min(stack.getCount(), spaceInStack);
                stack.decrement(itemCount);
                itemStack.increment(itemCount);
                return true;
            }
        }
        return false;
    }
    static boolean isInventoryFull(Inventory inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch(slot -> {
            ItemStack itemStack = inventory.getStack(slot);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }
}
