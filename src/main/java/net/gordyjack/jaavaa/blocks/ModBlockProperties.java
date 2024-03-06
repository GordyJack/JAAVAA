package net.gordyjack.jaavaa.blocks;

import net.gordyjack.jaavaa.blocks.enums.*;
import net.minecraft.state.property.*;

public class ModBlockProperties {
    public static final EnumProperty<BlockSection> SECTION = EnumProperty.of("section", BlockSection.class);
    public static final EnumProperty<MiniBlockType> MINI_BLOCK_TYPE = EnumProperty.of("type", MiniBlockType.class);
    public static final IntProperty MINI_BLOCK_POSITION = IntProperty.of("position", 0b00000000, 0b11111111);
}
