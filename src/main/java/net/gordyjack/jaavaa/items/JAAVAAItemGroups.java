package net.gordyjack.jaavaa.items;

import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;

import java.util.*;

public class JAAVAAItemGroups {
    public static final List<RegistryKey<ItemGroup>> ITEM_GROUPS = new ArrayList<>();
    public static final RegistryKey<ItemGroup> JAAVAA_SLABS = registerKey("jaavaa_slabs");
    public static final RegistryKey<ItemGroup> JAAVAA_WALLS = registerKey("jaavaa_walls");
    public static final RegistryKey<ItemGroup> JAAVAA_STAIRS = registerKey("jaavaa_stairs");
    public static final RegistryKey<ItemGroup> JAAVAA_ITEMS = registerKey("jaavaa_items");
    
    /**
     * Registers all ItemGroups for ModBlocks.
     */
    public static void registerItemGroups() {
        JAAVAA.logInfo("Registering ItemGroups");
        
        Registry.register(Registries.ITEM_GROUP, JAAVAA_SLABS,
                ItemGroup.create(ItemGroup.Row.TOP, 0)
                        .displayName(Text.translatable("jaavaa.itemGroup.slabs"))
                        .icon(() -> new ItemStack(JAAVAABlocks.SLABS.get(0))).build());
        Registry.register(Registries.ITEM_GROUP, JAAVAA_WALLS,
                ItemGroup.create(ItemGroup.Row.TOP, 1)
                        .displayName(Text.translatable("jaavaa.itemGroup.walls"))
                        .icon(() -> new ItemStack(JAAVAABlocks.WALLS.get(0))).build());
        Registry.register(Registries.ITEM_GROUP, JAAVAA_STAIRS,
                ItemGroup.create(ItemGroup.Row.TOP, 2)
                        .displayName(Text.translatable("jaavaa.itemGroup.stairs"))
                        .icon(() -> new ItemStack(JAAVAABlocks.STAIRS.get(0))).build());
        Registry.register(Registries.ITEM_GROUP, JAAVAA_ITEMS,
                ItemGroup.create(ItemGroup.Row.TOP, 3)
                        .displayName(Text.translatable("jaavaa.itemGroup.items"))
                        .icon(() -> new ItemStack(JAAVAAItems.ITEMS.get(0))).build());
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
            addToGroup(item, JAAVAA_ITEMS);
        }
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
    
    private static RegistryKey<ItemGroup> registerKey(String name) {
        RegistryKey<ItemGroup> returnKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, JAAVAA.getID(name));
        ITEM_GROUPS.add(returnKey);
        return returnKey;
    }
}
