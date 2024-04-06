package net.gordyjack.jaavaa.mixinterfaces;

import net.minecraft.util.math.*;

public interface ItemEntityMixinInterface {
    default Vec3d jaavaa$getOldPos() {
        return Vec3d.ZERO;
    }
    default void jaavaa$setOldPos(Vec3d pos) {
    }
    default boolean jaavaa$delayEnded() {
        return false;
    }
    default void jaavaa$setDelayEnded(boolean ended) {
    }
}
