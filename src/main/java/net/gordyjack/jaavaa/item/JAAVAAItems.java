package net.gordyjack.jaavaa.item;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.item.custom.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class JAAVAAItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item STARSTEEL_BLOCK = registerItem("starsteel_block", new BlockItem(
            JAAVAABlocks.STARSTEEL_BLOCK, new Item.Settings().rarity(Rarity.RARE)
    ));
    public static final Item STARSTEEL_INGOT = registerItem("starsteel_ingot", new Item(
            new Item.Settings().rarity(Rarity.RARE)
    ));
    public static final Item STARSTEEL_NUGGET = registerItem("starsteel_nugget", new GlintingItem(
            new Item.Settings().rarity(Rarity.RARE)
    ));
    public static final Item ALLAY_ESSENCE = registerItem("allay_essence", new Item(
            new Item.Settings()
    ));
    public static final Item CREATIVE_COAL = registerItem("creative_coal", new Item(
            new Item.Settings().rarity(Rarity.EPIC)
    ));
    public static final Item EMPTY_PERSONAL_COLLECTOR = registerItem("empty_personal_collector", new EmptyCollectorItem(
            new Item.Settings().maxCount(16).fireproof()
    ));
    public static final Item EMPTY_PERSONAL_COLLECTOR_GLINTING = registerItem("empty_personal_collector_glinting", new GlintingItem(
            new Item.Settings().maxCount(0).fireproof()
    ));
    public static final Item EMPTY_ENDER_COLLECTOR = registerItem("empty_ender_collector", new Item(
            new Item.Settings().maxCount(0).fireproof()
    ));
    public static final Item EMPTY_ENDER_COLLECTOR_GLINTING = registerItem("empty_ender_collector_glinting", new GlintingItem(
            new Item.Settings().maxCount(0).fireproof()
    ));
    public static final Item PERSONAL_ALLAY_COLLECTOR = registerItem("personal_allay_collector", new AllayCollectorItem(
            JAAVAABlocks.ALLAY_COLLECTOR,
            new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof().recipeRemainder(EMPTY_PERSONAL_COLLECTOR)
    ));
    public static final Item PERSONAL_ENDER_COLLECTOR = registerItem("personal_ender_collector", new EnderCollectorItem(
            JAAVAABlocks.ENDER_COLLECTOR,
            new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof().recipeRemainder(EMPTY_PERSONAL_COLLECTOR)
    ));
    
    private static Item registerItem(String name, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, JAAVAA.getID(name), item);
    }
    public static void init() {
        JAAVAA.logInfo("Initializing items");
    }
}
