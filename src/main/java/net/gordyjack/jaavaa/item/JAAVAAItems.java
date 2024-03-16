package net.gordyjack.jaavaa.item;

import net.fabricmc.fabric.api.item.v1.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.gordyjack.jaavaa.item.custom.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class JAAVAAItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    
    public static final Item STARSTEEL_INGOT = registerItem("starsteel_ingot", new GlintingItem(
            new FabricItemSettings().rarity(Rarity.RARE)
    ));
    public static final Item STARSTEEL_NUGGET = registerItem("starsteel_nugget", new GlintingItem(
            new FabricItemSettings().rarity(Rarity.RARE)
    ));
    public static final Item ALLAY_ESSENCE = registerItem("allay_essence", new Item(
            new FabricItemSettings()
    ));
    public static final Item PERSONAL_ALLAY_COLLECTOR = registerItem("personal_allay_collector", new AllayCollectorItem(
            JAAVAABlocks.ALLAY_COLLECTOR, new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof()
    ));
    public static final Item PERSONAL_ENDER_COLLECTOR = registerItem("personal_ender_collector", new EnderCollectorItem(
            JAAVAABlocks.ENDER_COLLECTOR, new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof()
    ));
    public static final Item EMPTY_PERSONAL_COLLECTOR = registerItem("empty_personal_collector", new EmptyCollectorItem(
            new FabricItemSettings().maxCount(16).fireproof(), (AbstractCollectorItem) PERSONAL_ALLAY_COLLECTOR
    ));
    
    private static Item registerItem(String name, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, JAAVAA.getID(name), item);
    }
    public static void registerItems() {
        JAAVAA.logInfo("Registering items");
    }
}
