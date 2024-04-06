package net.gordyjack.jaavaa;

import net.fabricmc.api.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.block.custom.entity.*;
import net.gordyjack.jaavaa.data.*;
import net.gordyjack.jaavaa.item.*;
import net.gordyjack.jaavaa.utils.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;
import org.slf4j.*;

public class JAAVAA
		implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jaavaa";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		JAAVAABlocks.init();
		JAAVAABlockEntityTypes.init();
		JAAVAAItems.init();
		JAAVAAItemGroups.init();
		JAAVAARegistries.init();
		JAAVAAComponents.init();
		
		JAAVAALootTableModifiers.modifyLootTables();
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