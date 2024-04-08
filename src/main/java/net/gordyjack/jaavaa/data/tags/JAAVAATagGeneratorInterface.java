package net.gordyjack.jaavaa.data.tags;

import net.gordyjack.jaavaa.*;
import net.minecraft.registry.tag.*;

public interface JAAVAATagGeneratorInterface<T> {
    default void generateTags() {
        JAAVAA.logInfo("Registering Tags");
    }
    default TagKey<T> registerModTagKey(String name) {
        return registerTagKey(JAAVAA.MOD_ID, name);
    }
    default TagKey<T> registerCommonTagKey(String name) {
        return registerTagKey("c", name);
    }
    default TagKey<T> registerMinecraftTagKey(String name) {
        return registerTagKey("minecraft", name);
    }
    TagKey<T> registerTagKey(String namespace, String name);
}
