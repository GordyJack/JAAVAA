package net.gordyjack.jaavaa;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.gordyjack.jaavaa.data.*;
import net.gordyjack.jaavaa.data.lang.*;
import net.minecraft.data.*;

public class JAAVAADataGenerator implements DataGeneratorEntrypoint {
	public DataProvider enUSLangProvider;
	public DataProvider modelProvider;
	public DataProvider recipeProvider;
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		
		enUSLangProvider = pack.addProvider(ENUSLangProvider::new);
		modelProvider = pack.addProvider(ModModelProvider::new);
		recipeProvider = pack.addProvider(ModRecipeProvider::new);
	}
}
