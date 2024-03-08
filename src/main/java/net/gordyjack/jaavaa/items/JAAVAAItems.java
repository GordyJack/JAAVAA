package net.gordyjack.jaavaa.items;

import net.fabricmc.fabric.api.item.v1.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.items.custom.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class JAAVAAItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    
    public static final Item STARSTEEL_INGOT = registerItem("starsteel_ingot", new GlintingItem(
            new FabricItemSettings().rarity(Rarity.EPIC)
    ));
    
    private static Item registerItem (String name, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, JAAVAA.getID(name), item);
    }
    public static void registerItems() {
        JAAVAA.logInfo("Registering items");
    }
}
