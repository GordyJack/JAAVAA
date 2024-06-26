package net.gordyjack.jaavaa;

import net.fabricmc.api.ModInitializer;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.gordyjack.jaavaa.block.custom.entity.JAAVAABlockEntityTypes;
import net.gordyjack.jaavaa.data.JAAVAALootTableModifiers;
import net.gordyjack.jaavaa.item.JAAVAAItemGroups;
import net.gordyjack.jaavaa.item.JAAVAAItems;
import net.gordyjack.jaavaa.utils.JAAVAARegistries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAAVAA
		implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jaavaa";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		JAAVAABlocks.registerBlocks();
		JAAVAABlockEntityTypes.registerBlockEntityTypes();
		JAAVAAItems.registerItems();
		JAAVAAItemGroups.registerItemGroups();
		JAAVAALootTableModifiers.modifyLootTables();
		JAAVAARegistries.register();
	}

	public static @NotNull Identifier getID(String namespace, String name) {
		return new Identifier(namespace, name);
	}
	public static @NotNull Identifier getID(String name) {
		return getID(MODID, name);
	}
	public static @NotNull Identifier getGameID(String name) {
		return getID("minecraft", name);
	}
	
	public static void logInfo(String message) {
		LOGGER.info(message);
	}
	public static void logError(String message) {
	    LOGGER.error(message);
	}
	public static void logWarn(String message) {
	    LOGGER.warn(message);
	}
	public static void logDebug(String message) {
	    LOGGER.debug(message);
	}
}