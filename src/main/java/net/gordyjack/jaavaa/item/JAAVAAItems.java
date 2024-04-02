package net.gordyjack.jaavaa.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.gordyjack.jaavaa.item.custom.AllayCollectorItem;
import net.gordyjack.jaavaa.item.custom.EmptyCollectorItem;
import net.gordyjack.jaavaa.item.custom.EnderCollectorItem;
import net.gordyjack.jaavaa.item.custom.GlintingItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public class JAAVAAItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item STARSTEEL_BLOCK = registerItem("starsteel_block", new BlockItem(
            JAAVAABlocks.STARSTEEL_BLOCK, new FabricItemSettings().rarity(Rarity.RARE)
    ));
    public static final Item STARSTEEL_INGOT = registerItem("starsteel_ingot", new Item(
            new FabricItemSettings().rarity(Rarity.RARE)
    ));
    public static final Item STARSTEEL_NUGGET = registerItem("starsteel_nugget", new GlintingItem(
            new FabricItemSettings().rarity(Rarity.RARE)
    ));
    public static final Item ALLAY_ESSENCE = registerItem("allay_essence", new Item(
            new FabricItemSettings()
    ));
    public static final Item CREATIVE_COAL = registerItem("creative_coal", new Item(
            new FabricItemSettings().rarity(Rarity.EPIC)
    ));
    public static final Item EMPTY_PERSONAL_COLLECTOR = registerItem("empty_personal_collector", new EmptyCollectorItem(
            new FabricItemSettings().maxCount(16).fireproof()
    ));
    public static final Item EMPTY_PERSONAL_COLLECTOR_GLINTING = registerItem("empty_personal_collector_glinting", new GlintingItem(
            new FabricItemSettings().maxCount(0).fireproof()
    ));
    public static final Item EMPTY_ENDER_COLLECTOR = registerItem("empty_ender_collector", new Item(
            new FabricItemSettings().maxCount(0).fireproof()
    ));
    public static final Item EMPTY_ENDER_COLLECTOR_GLINTING = registerItem("empty_ender_collector_glinting", new GlintingItem(
            new FabricItemSettings().maxCount(0).fireproof()
    ));
    public static final Item PERSONAL_ALLAY_COLLECTOR = registerItem("personal_allay_collector", new AllayCollectorItem(
            JAAVAABlocks.ALLAY_COLLECTOR, new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof().recipeRemainder(EMPTY_PERSONAL_COLLECTOR)
    ));
    public static final Item PERSONAL_ENDER_COLLECTOR = registerItem("personal_ender_collector", new EnderCollectorItem(
            JAAVAABlocks.ENDER_COLLECTOR, new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof().recipeRemainder(EMPTY_PERSONAL_COLLECTOR)
    ));
    
    private static Item registerItem(String name, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, JAAVAA.getID(name), item);
    }
    public static void registerItems() {
        JAAVAA.logInfo("Registering items");
    }
}
