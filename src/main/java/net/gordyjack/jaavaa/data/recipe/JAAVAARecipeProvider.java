package net.gordyjack.jaavaa.data.recipe;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.item.*;
import net.minecraft.block.*;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.*;
import net.minecraft.recipe.book.*;
import net.minecraft.util.*;

import java.util.function.*;

public class JAAVAARecipeProvider
extends FabricRecipeProvider {
    public JAAVAARecipeProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generate(RecipeExporter exporter) {
        offerShapedCraftingRecipe(exporter, " # |#A#| # ", RecipeCategory.MISC, JAAVAAItems.STARSTEEL_INGOT, 2,
                Items.NETHERITE_INGOT, Items.NETHER_STAR);
        offerShapedCraftingRecipe(exporter, "#A#|BCB|#A#", RecipeCategory.TOOLS, JAAVAAItems.EMPTY_PERSONAL_COLLECTOR, 1,
                Blocks.OBSIDIAN, Items.NETHERITE_INGOT, Items.GLASS_PANE, JAAVAAItems.ALLAY_ESSENCE);
        
        //Starsteel Compacting
        generate3x3CompactingRecipes(exporter, RecipeCategory.MISC, JAAVAAItems.STARSTEEL_NUGGET, RecipeCategory.MISC, JAAVAAItems.STARSTEEL_INGOT);
    }
    
    private static void generate3x3CompactingRecipes(RecipeExporter exporter, RecipeCategory baseCategory, Item baseItem,
                                                     RecipeCategory compactCategory, Item compactItem) {
        offerShapelessCraftingRecipe(exporter, baseCategory, baseItem, 9, compactItem);
        offerShapedCraftingRecipe(exporter, "###|###|###", compactCategory, compactItem, 1, baseItem);
    }
    /**
     * Construct a new shaped crafting recipe.
     *
     * @param exporter The exporter for recipes
     * @param pattern The string that represents the pattern of the crafting grid.
     *                Each filled slot should be represented as '#' for inputs[0], 'A' for inputs[1],
     *                'B' for inputs[2], or 'C' for inputs[3].
     *                Each row of the grid should be separated with a '|'
     * @param output The item for the crafting recipe to output
     * @param outputCount The number of output items received.
     * @param inputs The items used in the crafting recipe.
     *
     * @throws IllegalArgumentException if inputs length is not between 1 and 4 inclusive.
     */
    private static void offerShapedCraftingRecipe(RecipeExporter exporter, String pattern, RecipeCategory category,
                                           ItemConvertible output, int outputCount, ItemConvertible... inputs) {
        String[] patterns = getPatterns(pattern);
        Identifier identifier = getRecipeID(output);
        switch (inputs.length) {
            case 1 -> ShapedRecipeJsonBuilder.create(category, output, outputCount)
                    .input('#', inputs[0])
                    .pattern(patterns[0]).pattern(patterns[1]).pattern(patterns[2])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .offerTo(exporter, getConvertID(output, inputs[0]));
            case 2 -> ShapedRecipeJsonBuilder.create(category, output, outputCount)
                    .input('#', inputs[0])
                    .input('A', inputs[1])
                    .pattern(patterns[0]).pattern(patterns[1]).pattern(patterns[2])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .offerTo(exporter, identifier);
            case 3 -> ShapedRecipeJsonBuilder.create(category, output, outputCount)
                    .input('#', inputs[0])
                    .input('A', inputs[1])
                    .input('B', inputs[2])
                    .pattern(patterns[0]).pattern(patterns[1]).pattern(patterns[2])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .criterion(hasItem(inputs[2]), conditionsFromItem(inputs[2]))
                    .offerTo(exporter, identifier);
            case 4 -> ShapedRecipeJsonBuilder.create(category, output, outputCount)
                    .input('#', inputs[0])
                    .input('A', inputs[1])
                    .input('B', inputs[2])
                    .input('C', inputs[3])
                    .pattern(patterns[0]).pattern(patterns[1]).pattern(patterns[2])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .criterion(hasItem(inputs[2]), conditionsFromItem(inputs[2]))
                    .criterion(hasItem(inputs[3]), conditionsFromItem(inputs[3]))
                    .offerTo(exporter, identifier);
            default -> throw new IllegalArgumentException("Inputs has wrong number of arguments");
        }
    }
    private static void offerShapelessCraftingRecipe(RecipeExporter exporter, RecipeCategory category,
                                                     ItemConvertible output, int outputCount, ItemConvertible... inputs) {
        Identifier identifier = getRecipeID(output);
        switch (inputs.length) {
            case 1 -> ShapelessRecipeJsonBuilder.create(category, output, outputCount)
                    .input(inputs[0])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .offerTo(exporter, getConvertID(output, inputs[0]));
            case 2 -> ShapelessRecipeJsonBuilder.create(category, output, outputCount)
                    .input(inputs[0])
                    .input(inputs[1])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .offerTo(exporter, identifier);
            case 3 -> ShapelessRecipeJsonBuilder.create(category, output, outputCount)
                    .input(inputs[0])
                    .input(inputs[1])
                    .input(inputs[2])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .criterion(hasItem(inputs[2]), conditionsFromItem(inputs[2]))
                    .offerTo(exporter, identifier);
            case 4 -> ShapelessRecipeJsonBuilder.create(category, output, outputCount)
                    .input(inputs[0])
                    .input(inputs[1])
                    .input(inputs[2])
                    .input(inputs[3])
                    .criterion(hasItem(inputs[0]), conditionsFromItem(inputs[0]))
                    .criterion(hasItem(inputs[1]), conditionsFromItem(inputs[1]))
                    .criterion(hasItem(inputs[2]), conditionsFromItem(inputs[2]))
                    .criterion(hasItem(inputs[3]), conditionsFromItem(inputs[3]))
                    .offerTo(exporter, identifier);
            default -> throw new IllegalArgumentException("Inputs has wrong number of arguments");
        }
    }
    
    private static Identifier getConvertID(ItemConvertible output, ItemConvertible input) {
        return JAAVAA.getID(convertBetween(output, input));
    }
    private static Identifier getRecipeID(ItemConvertible output) {
        return JAAVAA.getID(getItemPath(output));
    }
    private static Identifier getRecipeID(ItemConvertible output, String appendType) {
        return JAAVAA.getID(getItemPath(output) + appendType);
    }
    private static String[] getPatterns(String pattern) {
        String[] patterns = new String[3];
        patterns[0] = pattern.substring(0, pattern.indexOf('|'));
        patterns[1] = pattern.substring(pattern.indexOf('|') + 1, pattern.lastIndexOf('|'));
        patterns[2] = pattern.substring(pattern.lastIndexOf('|') + 1);
        return patterns;
    }
}
