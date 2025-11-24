package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.gordyjack.jaavaa.data.model.*;
import net.minecraft.client.data.models.*;

public class JAAVAADataGenerator implements DataGeneratorEntrypoint {
	public static ModelProvider modelProvider;
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		modelProvider = fabricDataGenerator.createPack().addProvider(JAAVAAModelProvider::new);
	}
}
