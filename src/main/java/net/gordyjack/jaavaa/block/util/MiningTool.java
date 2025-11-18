package net.gordyjack.jaavaa.block.util;

import net.minecraft.util.*;
import org.jetbrains.annotations.*;

public enum MiningTool implements StringRepresentable {
    AXE("axe"),
    HOE("hoe"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    SWORD("sword");

    private final String NAME;

    MiningTool(String name) {
        this.NAME = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.NAME;
    }
}
