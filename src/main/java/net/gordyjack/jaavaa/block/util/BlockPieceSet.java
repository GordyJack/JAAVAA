package net.gordyjack.jaavaa.block.util;

import net.gordyjack.jaavaa.block.custom.*;
import net.minecraft.world.level.block.*;

import java.util.*;

public record BlockPieceSet(Block parent, Blocktant blocktant, PanelBlock panel, MiningTool tool, MiningLevel level) {
    public List<Block> getPieces() {
        return List.of(blocktant, panel);
    }
}
