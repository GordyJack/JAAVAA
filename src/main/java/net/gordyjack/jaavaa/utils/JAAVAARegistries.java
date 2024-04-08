package net.gordyjack.jaavaa.utils;

import net.fabricmc.fabric.api.registry.*;
import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.item.*;

public class JAAVAARegistries {
    private static void registerFuels() {
        JAAVAA.logInfo("Registering Fuels for " + JAAVAA.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(JAAVAAItems.CREATIVE_COAL, 32767);
    }

    public static void init() {
        JAAVAA.logInfo("Initializing registries");
        registerFuels();
    }
}
