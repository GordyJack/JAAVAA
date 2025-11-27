package net.gordyjack.jaavaa.block;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavaa.block.util.*;
import net.gordyjack.jaavll.util.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.List;
import java.util.function.*;

public final class JAAVAABlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<BlockPieceSet> BLOCK_PIECE_SETS = new ArrayList<>();
    public static final BlockPieceSet STONE_SET = registerBlockPieceSet(Blocks.STONE, MiningTool.PICKAXE, MiningLevel.STONE);
    public static final BlockPieceSet SANDSTONE_SET = registerBlockPieceSet(Blocks.SANDSTONE, MiningTool.PICKAXE, MiningLevel.STONE);
    public static final BlockPieceSet CHISELED_SANDSTONE_SET = registerBlockPieceSet(Blocks.CHISELED_SANDSTONE, MiningTool.PICKAXE, MiningLevel.STONE);
    public static final BlockPieceSet SMOOTH_SANDSTONE_SET = registerBlockPieceSet(Blocks.SMOOTH_SANDSTONE, MiningTool.PICKAXE, MiningLevel.STONE);
    public static final BlockPieceSet CUT_SANDSTONE_SET = registerBlockPieceSet(Blocks.CUT_SANDSTONE, MiningTool.PICKAXE, MiningLevel.STONE);
    public static final BlockPieceSet IRON_BLOCK_SET = registerBlockPieceSet(Blocks.IRON_BLOCK, MiningTool.PICKAXE, MiningLevel.IRON);
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
        String parentName = IDUtil.nameFromId(parent);
        BlockBehaviour.Properties parentBehaviour = BlockBehaviour.Properties.ofFullCopy(parent);
        BeamBlock beam = (BeamBlock) registerBlock(parentName + "_beam", BeamBlock::new, parentBehaviour);
        Blocktant blocktant = (Blocktant) registerBlock(parentName + "_blocktant", Blocktant::new, parentBehaviour);
        PanelBlock panel = (PanelBlock) registerBlock(parentName + "_panel", PanelBlock::new, parentBehaviour);
        BlockPieceSet blockPieceSet = new BlockPieceSet(parent, beam, blocktant, panel, tool, level);
        BLOCK_PIECE_SETS.add(blockPieceSet);
        return blockPieceSet;
    }

    public static void init() {
        JAAVAA.log("Initializing Blocks", 'd');
    }
}
