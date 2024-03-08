package net.gordyjack.jaavaa;

import net.fabricmc.api.ModInitializer;

import net.gordyjack.jaavaa.blocks.*;
import net.gordyjack.jaavaa.items.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAAVAA implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jaavaa";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		JAAVAABlocks.registerBlocks();
		JAAVAAItems.registerItems();
		JAAVAAItemGroups.registerItemGroups();
	}
	
	@Contract("_,_ -> new")
	public static @NotNull Identifier getID(String namespace, String name) {
		return new Identifier(namespace, name);
	}
	@Contract("_ -> new")
	public static @NotNull Identifier getID(String name) {
		return getID(MODID, name);
	}
	
	public static void logInfo(String message) {
		LOGGER.info(message);
	}
	public static void logError(String message) {
	    LOGGER.error(message);
	}
}