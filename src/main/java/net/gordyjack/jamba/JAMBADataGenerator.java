package net.gordyjack.jamba;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.gordyjack.jamba.data.*;
import net.minecraft.data.*;

public class JAMBADataGenerator implements DataGeneratorEntrypoint {
	public DataProvider modelProvider;
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		
		modelProvider = pack.addProvider(ModModelProvider::new);
	}
}
