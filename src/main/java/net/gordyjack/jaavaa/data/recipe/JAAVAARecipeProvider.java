package net.gordyjack.jaavaa.data.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.gordyjack.jaavaa.item.JAAVAAItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

public class JAAVAARecipeProvider
extends FabricRecipeProvider {
    public JAAVAARecipeProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generate(RecipeExporter exporter) {
        //Starsteel Ingot Crafting Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, JAAVAAItems.STARSTEEL_INGOT, 2)
                .input('I', Items.NETHERITE_INGOT)
                .input('S', Items.NETHER_STAR)
                .pattern(" I ").pattern("ISI").pattern(" I ")
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAAItems.STARSTEEL_INGOT)));
        //Personal Collector Crafting Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, JAAVAAItems.EMPTY_PERSONAL_COLLECTOR, 1)
                .input('#', Blocks.OBSIDIAN)
                .input('A', Items.NETHERITE_SCRAP)
                .input('B', Items.GLASS_PANE)
                .input('C', JAAVAAItems.ALLAY_ESSENCE)
                .pattern("#A#").pattern("BCB").pattern("#A#")
                .criterion(hasItem(Items.NETHERITE_SCRAP), conditionsFromItem(Items.NETHERITE_SCRAP))
                .criterion(hasItem(JAAVAAItems.ALLAY_ESSENCE), conditionsFromItem(JAAVAAItems.ALLAY_ESSENCE))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAAItems.EMPTY_PERSONAL_COLLECTOR)));
        //Adjustable Lamp Crafting Recipes
        String lampPath = getItemPath(JAAVAABlocks.ADJUSTABLE_REDSTONE_LAMP);
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.ADJUSTABLE_REDSTONE_LAMP, 1)
                .input('L', Items.REDSTONE_LAMP)
                .input('Q', Items.QUARTZ)
                .input('B', ItemTags.BUTTONS)
                .pattern(" Q ").pattern("BLB").pattern(" Q ")
                .criterion(hasItem(Items.REDSTONE_LAMP), conditionsFromItem(Items.REDSTONE_LAMP))
                .criterion(hasItem(Items.COMPARATOR), conditionsFromItem(Items.COMPARATOR))
                .group(lampPath)
                .offerTo(exporter, JAAVAA.getID(lampPath));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.ADJUSTABLE_REDSTONE_LAMP, 1)
                .input('L', Items.REDSTONE_LAMP)
                .input('Q', Items.QUARTZ)
                .input('B', ItemTags.BUTTONS)
                .pattern(" B ").pattern("QLQ").pattern(" B ")
                .criterion(hasItem(Items.REDSTONE_LAMP), conditionsFromItem(Items.REDSTONE_LAMP))
                .criterion(hasItem(Items.COMPARATOR), conditionsFromItem(Items.COMPARATOR))
                .group(lampPath)
                .offerTo(exporter, JAAVAA.getID(lampPath + "_alt"));
        //Encased Redstone Pillar Crafting Recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR, 6)
                .input('R', Blocks.REDSTONE_BLOCK)
                .input('Q', Items.QUARTZ)
                .pattern("QRQ").pattern("QRQ").pattern("QRQ")
                .criterion(hasItem(Blocks.REDSTONE_BLOCK), conditionsFromItem(Blocks.REDSTONE_BLOCK))
                .criterion(hasItem(Blocks.QUARTZ_PILLAR), conditionsFromItem(Blocks.QUARTZ_PILLAR))
                .group(getItemPath(JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR, 6)
                .input('R', Blocks.REDSTONE_BLOCK)
                .input('Q', Items.QUARTZ)
                .pattern("QQQ").pattern("RRR").pattern("QQQ")
                .criterion(hasItem(Blocks.REDSTONE_BLOCK), conditionsFromItem(Blocks.REDSTONE_BLOCK))
                .criterion(hasItem(Blocks.QUARTZ_PILLAR), conditionsFromItem(Blocks.QUARTZ_PILLAR))
                .group(getItemPath(JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAABlocks.QUARTZ_ENCASED_REDSTONE_PILLAR) + "_alt"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR, 6)
                .input('R', Blocks.REDSTONE_BLOCK)
                .input('A', Items.ANCIENT_DEBRIS)
                .pattern("ARA").pattern("ARA").pattern("ARA")
                .criterion(hasItem(Blocks.REDSTONE_BLOCK), conditionsFromItem(Blocks.REDSTONE_BLOCK))
                .criterion(hasItem(Blocks.ANCIENT_DEBRIS), conditionsFromItem(Blocks.ANCIENT_DEBRIS))
                .group(getItemPath(JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR, 6)
                .input('R', Blocks.REDSTONE_BLOCK)
                .input('A', Items.ANCIENT_DEBRIS)
                .pattern("AAA").pattern("RRR").pattern("AAA")
                .criterion(hasItem(Blocks.REDSTONE_BLOCK), conditionsFromItem(Blocks.REDSTONE_BLOCK))
                .criterion(hasItem(Blocks.ANCIENT_DEBRIS), conditionsFromItem(Blocks.ANCIENT_DEBRIS))
                .group(getItemPath(JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR))
                .offerTo(exporter, JAAVAA.getID(getItemPath(JAAVAABlocks.ANCIENT_DEBRIS_ENCASED_REDSTONE_PILLAR) + "_alt"));
        
        //Starsteel Compacting
        generate3x3CompactingRecipes(exporter, RecipeCategory.MISC, JAAVAAItems.STARSTEEL_NUGGET, RecipeCategory.MISC, JAAVAAItems.STARSTEEL_INGOT);
        generate3x3CompactingRecipes(exporter, RecipeCategory.MISC, JAAVAAItems.STARSTEEL_INGOT, RecipeCategory.BUILDING_BLOCKS, JAAVAABlocks.STARSTEEL_BLOCK);
    }
    
    private static void generate3x3CompactingRecipes(RecipeExporter exporter, RecipeCategory baseCategory, ItemConvertible baseItem,
                                                     RecipeCategory compactCategory, ItemConvertible compactItem) {
        ShapelessRecipeJsonBuilder.create(baseCategory, baseItem, 9)
                .input(compactItem)
                .criterion(hasItem(compactItem), conditionsFromItem(compactItem))
                .offerTo(exporter, JAAVAA.getID(convertBetween(baseItem, compactItem)));
        ShapedRecipeJsonBuilder.create(compactCategory, compactItem, 1)
                .input('#', baseItem)
                .pattern("###").pattern("###").pattern("###")
                .criterion(hasItem(baseItem), conditionsFromItem(baseItem))
                .offerTo(exporter, JAAVAA.getID(convertBetween(compactItem, baseItem)));
    }
}
