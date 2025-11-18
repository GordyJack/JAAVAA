package net.gordyjack.jaavaa.block.util;

import net.minecraft.world.level.block.*;

import java.util.*;

public record BlockPieceSet(Block parent, Block blocktant, Block panel, MiningTool tool, MiningLevel level) {
    public List<Block> getPieces() {
        return List.of(blocktant, panel);
    }
}
