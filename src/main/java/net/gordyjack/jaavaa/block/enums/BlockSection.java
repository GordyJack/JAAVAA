package net.gordyjack.jaavaa.block.enums;

import net.minecraft.util.*;

public enum BlockSection
implements StringIdentifiable {
    DOWN_NORTH_WEST("dnw"),
    DOWN_NORTH_EAST("dne"),
    DOWN_SOUTH_WEST("dsw"),
    DOWN_SOUTH_EAST("dse"),
    UP_NORTH_WEST("unw"),
    UP_NORTH_EAST("une"),
    UP_SOUTH_WEST("usw"),
    UP_SOUTH_EAST("use");
    
    private final String NAME;
    
    BlockSection(String name) {
        this.NAME = name;
    }
    
    @Override
    public String asString() {
        return this.NAME;
    }
    @Override
    public String toString() {
        return this.asString();
    }
}
