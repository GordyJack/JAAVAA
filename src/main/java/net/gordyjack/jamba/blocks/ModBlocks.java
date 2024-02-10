package net.gordyjack.jamba.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.gordyjack.jamba.JAMBA;

import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    
    public static final Block TEST_BLOCK = registerBlock("jamba_test_block", new Block(
            FabricBlockSettings.copyOf(Blocks.STONE)
    ));
    
    private static Block registerBlock(String name, Block block) {
        BLOCKS.add(block);
        
        registerBlockItem(name, block);
        return registerBlockWithoutItem(name, block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, JAMBA.getID(name), new BlockItem(block, new FabricItemSettings()));
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, JAMBA.getID(name), block);
    }
    
    public static void registerBlocks() {
        JAMBA.logInfo("Registering ModBlocks");
    }
}
