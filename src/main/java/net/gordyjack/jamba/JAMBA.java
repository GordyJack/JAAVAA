package net.gordyjack.jamba;

import net.fabricmc.api.ModInitializer;

import net.gordyjack.jamba.blocks.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAMBA implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "jamba";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModBlocks.registerBlocks();
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
}