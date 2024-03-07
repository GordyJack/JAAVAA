package net.gordyjack.jaavaa.data.tags;

import net.gordyjack.jaavaa.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

public interface ModTagInterface<T> {
    default void generateTags() {
        JAAVAA.logInfo("Registering Tags");
    }
    default TagKey<T> registerModTagKey(String name) {
        return registerTagKey(JAAVAA.MODID, name);
    }
    default TagKey<T> registerCommonTagKey(String name) {
        return registerTagKey("c", name);
    }
    default TagKey<T> registerMinecraftTagKey(String name) {
        return registerTagKey("minecraft", name);
    }
    TagKey<T> registerTagKey(String namespace, String name);
}
