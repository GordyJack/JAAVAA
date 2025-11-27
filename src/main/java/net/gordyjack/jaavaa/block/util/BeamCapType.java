package net.gordyjack.jaavaa.block.util;

import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public enum BeamCapType implements StringRepresentable {
    NONE("none"),
    BOTTOM("bottom"),
    TOP("top"),
    BOTH("both");
    private final String name;
    BeamCapType(String name) {
        this.name = name;
    }
    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
