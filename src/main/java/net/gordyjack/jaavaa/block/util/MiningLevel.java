package net.gordyjack.jaavaa.block.util;

import net.minecraft.util.*;

public enum MiningLevel {
    STONE(0),
    IRON(1),
    DIAMOND(2),
    NETHERITE(3);

    private final int LEVEL;

    MiningLevel(int level) {
        this.LEVEL = level;
    }

    public int getLevel() {
        return this.LEVEL;
    }
}
