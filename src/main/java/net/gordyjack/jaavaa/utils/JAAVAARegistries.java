package net.gordyjack.jaavaa.utils;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.item.JAAVAAItems;

public class JAAVAARegistries {
    private static void registerFuels() {
        JAAVAA.logInfo("Registering Fuels for " + JAAVAA.MODID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(JAAVAAItems.CREATIVE_COAL, Integer.MAX_VALUE);
    }

    public static void register() {
        JAAVAA.logInfo("Registering Registries");
        registerFuels();
    }
}
