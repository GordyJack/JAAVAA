package net.gordyjack.jaavaa.item;

import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;

import java.util.*;

public class JAAVAAItemGroups {
    public static final List<RegistryKey<ItemGroup>> ITEM_GROUPS = new ArrayList<>();
    public static final RegistryKey<ItemGroup> JAAVAA_SLABS = registerItemGroup("jaavaa_slabs", JAAVAABlocks.SLABS.get(0));
    public static final RegistryKey<ItemGroup> JAAVAA_WALLS = registerItemGroup("jaavaa_walls", JAAVAABlocks.WALLS.get(0));
    public static final RegistryKey<ItemGroup> JAAVAA_STAIRS = registerItemGroup("jaavaa_stairs", JAAVAABlocks.STAIRS.get(0));
    public static final RegistryKey<ItemGroup> JAAVAA_GROUP = registerItemGroup("jaavaa", JAAVAAItems.ITEMS.get(0));
    
    /**
     * Registers all ItemGroups for ModBlocks.
     */
    public static void registerItemGroups() {
        JAAVAA.logInfo("Registering ItemGroups");

        for (Block slabBlock : JAAVAABlocks.SLABS) {
            addToGroup(slabBlock, JAAVAA_SLABS);
        }
        for (Block wallBlock : JAAVAABlocks.WALLS) {
            addToGroup(wallBlock, JAAVAA_WALLS);
        }
        for (Block stairsBlock : JAAVAABlocks.STAIRS) {
            addToGroup(stairsBlock, JAAVAA_STAIRS);
        }
        for (Item item : JAAVAAItems.ITEMS) {
            if (item == JAAVAAItems.EMPTY_PERSONAL_COLLECTOR_GLINTING
                    || item == JAAVAAItems.EMPTY_ENDER_COLLECTOR
                    || item == JAAVAAItems.EMPTY_ENDER_COLLECTOR_GLINTING) {
                continue;
            }
            addToGroup(item, JAAVAA_GROUP);
        }
        addToGroup(JAAVAABlocks.ADJUSTABLE_REDSTONE_LAMP, JAAVAA_GROUP);

    }
    private static int currentColumn = 0;
    private static RegistryKey<ItemGroup> registerItemGroup(String name, ItemConvertible icon) {
        var returnKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, JAAVAA.getID(name));
        Registry.register(Registries.ITEM_GROUP, returnKey,
                ItemGroup.create(ItemGroup.Row.TOP, currentColumn++)
                        .displayName(Text.translatable("itemGroup.jaavaa." + name))
                        .icon(() -> new ItemStack(icon)).build());
        ITEM_GROUPS.add(returnKey);
        return returnKey;
    }
    /**
     * Adds a given Block to a given ItemGroup.
     *
     * @param itemConvertible The item to add
     * @param group The ItemGroup to add the Block to
     */
    private static void addToGroup(ItemConvertible itemConvertible, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(itemConvertible.asItem()));
    }
    private static void addToGroupAfter(ItemConvertible addAfterItem, ItemConvertible itemConvertible, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(addAfterItem, itemConvertible));
    }
}
