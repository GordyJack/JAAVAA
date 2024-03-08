package net.gordyjack.jaavaa;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.gordyjack.jaavaa.data.*;
import net.gordyjack.jaavaa.data.lang.*;
import net.gordyjack.jaavaa.data.tags.*;
import net.minecraft.data.*;

public class JAAVAADataGenerator implements DataGeneratorEntrypoint {
	public DataProvider enUSLangProvider;
	public DataProvider modelProvider;
	public DataProvider recipeProvider;
	public DataProvider blockTagProvider;
	public DataProvider blockLootTableProvider;
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		
		enUSLangProvider = pack.addProvider(JAAVAALangProviderEnUs::new);
		modelProvider = pack.addProvider(JAAVAAModelProvider::new);
		recipeProvider = pack.addProvider(JAAVAARecipeProvider::new);
		blockTagProvider = pack.addProvider(JAAVAABlockTagGenerator::new);
		blockLootTableProvider = pack.addProvider(JAAVAABlockLootTableGenerator::new);
	}
}
