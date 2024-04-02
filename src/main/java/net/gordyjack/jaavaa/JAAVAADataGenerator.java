package net.gordyjack.jaavaa;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.gordyjack.jaavaa.data.JAAVAABlockLootTableGenerator;
import net.gordyjack.jaavaa.data.JAAVAAModelProvider;
import net.gordyjack.jaavaa.data.lang.JAAVAALangProviderEnUs;
import net.gordyjack.jaavaa.data.recipe.JAAVAARecipeProvider;
import net.gordyjack.jaavaa.data.tags.JAAVAABlockTagGenerator;
import net.gordyjack.jaavaa.data.tags.JAAVAAItemTagGenerator;
import net.minecraft.data.DataProvider;

public class JAAVAADataGenerator implements DataGeneratorEntrypoint {
	public DataProvider enUSLangProvider;
	public DataProvider modelProvider;
	public DataProvider recipeProvider;
	public DataProvider blockTagProvider;
	public DataProvider itemTagProvider;
	public DataProvider blockLootTableProvider;
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		
		enUSLangProvider = pack.addProvider(JAAVAALangProviderEnUs::new);
		modelProvider = pack.addProvider(JAAVAAModelProvider::new);
		recipeProvider = pack.addProvider(JAAVAARecipeProvider::new);
		blockLootTableProvider = pack.addProvider(JAAVAABlockLootTableGenerator::new);
		blockTagProvider = pack.addProvider(JAAVAABlockTagGenerator::new);
		itemTagProvider = pack.addProvider(JAAVAAItemTagGenerator::new);
	}
}
