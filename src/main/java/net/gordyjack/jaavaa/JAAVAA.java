package net.gordyjack.jaavaa;

import net.fabricmc.api.ModInitializer;

import net.gordyjack.jaavaa.block.*;
import net.minecraft.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAAVAA implements ModInitializer {
	public static final String MOD_ID = "jaavaa";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		JAAVAABlocks.init();
	}

	public static void log(String message) {
		log(message,'i');
	}
	public static void logError(String message) {
		log(message, 'e');
	}
	public static void log(String message, char level) {
		switch (level) {
			case 'd' -> LOGGER.debug(message);
			case 'w' -> LOGGER.warn(message);
			case 'e' -> LOGGER.error(message);
			default -> LOGGER.info(message);
		}
	}
	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}