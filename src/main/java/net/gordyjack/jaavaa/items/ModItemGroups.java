package net.gordyjack.jaavaa.items;

import net.fabricmc.fabric.api.itemgroup.v1.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;

import java.util.*;

public class ModItemGroups {
    public static final List<RegistryKey<ItemGroup>> ITEM_GROUPS = new ArrayList<>();
    //public static final RegistryKey<ItemGroup> JAMBA_ITEM_GROUP = registerItemGroup(JAMBA.MOD_ID, ModBlocks.EXAMPLE_BLOCK.asItem());
    public static final RegistryKey<ItemGroup> JAAVAA_SLABS = registerKey("jaavaa_slabs");
    
    /**
     * Registers all ItemGroups for ModBlocks.
     */
    public static void registerItemGroups() {
        JAAVAA.logInfo("Registering ItemGroups");
        
        Registry.register(Registries.ITEM_GROUP, JAAVAA_SLABS,
                ItemGroup.create(ItemGroup.Row.TOP, 87)
                        .displayName(Text.translatable("jaavaa.itemGroup.slabs"))
                        .icon(() -> new ItemStack(ModBlocks.SLABS.get(0))).build());
        for (Block block : ModBlocks.SLABS) {
            addToGroup(block, JAAVAA_SLABS);
        }
    }
    /**
     * Adds a given Block to a given ItemGroup.
     *
     * @param block The Block to add
     * @param group The ItemGroup to add the Block to
     */
    private static void addToGroup(Block block, RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(block.asItem()));
    }
    
    private static RegistryKey<ItemGroup> registerKey(String name) {
        RegistryKey<ItemGroup> returnKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, JAAVAA.getID(name));
        ITEM_GROUPS.add(returnKey);
        return returnKey;
    }
}
