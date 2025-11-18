package net.gordyjack.jaavaa.block;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavaa.block.util.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.List;
import java.util.function.*;

public final class JAAVAABlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final BlockPieceSet STONE_SET = registerBlockPieceSet(Blocks.STONE, MiningTool.PICKAXE, MiningLevel.STONE);
    private JAAVAABlocks() {}


    private static Block registerBlock(String path, BlockBehaviour.Properties properties) {
        return registerBlock(path, Block::new, properties, BlockItem::new, new Item.Properties());
    }
    private static Block registerBlock(String path, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties) {
        return registerBlock(path, blockFactory, blockProperties, BlockItem::new, new Item.Properties());
    }
    private static Block registerBlock(String path, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties,
                                       Item.Properties itemProperties) {
        return registerBlock(path, blockFactory, blockProperties, null, itemProperties);
    }
    private static Block registerBlock(String path, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties,
                                       BiFunction<Block, Item.Properties, Item> blockItemFactory) {
        return registerBlock(path, blockFactory, blockProperties, blockItemFactory, null);
    }
    private static Block registerBlockWithoutItem(String path, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties) {
        return registerBlock(path, blockFactory, blockProperties, null, null);
    }
    private static Block registerBlock(String path, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties,
                                       @Nullable BiFunction<Block, Item.Properties, Item> blockItemFactory, @Nullable Item.Properties itemProperties) {
        final Block BLOCK = Blocks.register(ResourceKey.create(Registries.BLOCK, JAAVAA.id(path)), blockFactory, blockProperties);
        if (!(blockItemFactory == null && itemProperties == null)) {
            blockItemFactory = blockItemFactory == null ? BlockItem::new : blockItemFactory;
            itemProperties = itemProperties == null ? new Item.Properties() : itemProperties;
            Items.registerBlock(BLOCK, blockItemFactory, itemProperties);
        }
        BLOCKS.add(BLOCK);
        return BLOCK;
    }
    private static BlockPieceSet registerBlockPieceSet(Block parent, MiningTool tool, MiningLevel level) {
        String parentName = parent.getName().toString();
        BlockBehaviour.Properties parentBehaviour = BlockBehaviour.Properties.ofFullCopy(parent);
        Blocktant blocktant = (Blocktant) registerBlock(parentName + "_blocktant", Blocktant::new, parentBehaviour);
        Block panel = registerBlock(parentName + "_panel", Block::new, parentBehaviour);
        return new BlockPieceSet(parent, blocktant, panel, tool, level);
    }
}
