package net.gordyjack.jaavaa.block.enums;

import net.minecraft.util.*;

public enum MiniBlockType
implements StringIdentifiable {
    SINGLE("single"),
    STRAIGHT("straight"),
    //VERTICAL_STRAIGHT("vertical_straight"),
    DIAGONAL_0("diagonal_0"),
    DIAGONAL_1("diagonal_1"),
    DIAGONAL_2("diagonal_2"),
    FLAT_CORNER("flat_corner"),
    //VERTICAL_CORNER("vertical_corner"),
    SLAB("slab"),
    //VERTICAL_SLAB("vertical_slab"),
    STAIR("stair"),
    //VERTICAL_STAIR("vertical_stair"),
    FULL_CORNER("full_corner"),
    STAIR_OUTER_CORNER("stair_outer_corner"),
    SLAB_DIAGONAL("slab_diagonal"),
    STAIR_INNER_CORNER("stair_inner_corner"),
    FULL_CUBE("full_cube");
    private final String NAME;
    MiniBlockType(String name) {
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
