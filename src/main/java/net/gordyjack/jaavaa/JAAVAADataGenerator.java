package net.gordyjack.jaavaa;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.gordyjack.jaavaa.data.*;
import net.gordyjack.jaavaa.data.lang.*;
import net.gordyjack.jaavaa.data.recipe.*;
import net.gordyjack.jaavaa.data.tags.*;
import net.minecraft.data.*;

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
