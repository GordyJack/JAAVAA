package net.gordyjack.jaavaa.data.tags;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.Arrays;
import java.util.concurrent.*;

public class JAAVAABlockTagGenerator
extends FabricTagProvider.BlockTagProvider
implements JAAVAATagGeneratorInterface<Block> {
    public JAAVAABlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public final TagKey<Block> COMMON_GLASS = registerCommonTagKey("glass_blocks");
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for (Block block : JAAVAABlocks.BLOCKS) {
            String key = block.getTranslationKey();
            if (block instanceof SlabBlock) {
                getOrCreateTagBuilder(BlockTags.SLABS).add(block);
            }
            if (block instanceof WallBlock) {
                getOrCreateTagBuilder(BlockTags.WALLS).add(block);
            }
            if (block instanceof StairsBlock) {
                getOrCreateTagBuilder(BlockTags.STAIRS).add(block);
            }

            //TODO: all isIn tags don't work yet.
            JAAVAA.logError("Block: " + block.getTranslationKey()
                    + ", Parent: " + JAAVAABlocks.getParent(block).getTranslationKey()
                    + ", ParentDefaultState: " + JAAVAABlocks.getParent(block).getDefaultState()
                    + ", ParentTags: " + Arrays.toString(JAAVAABlocks.getParent(block).getDefaultState().streamTags().toArray()));
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.AXE_MINEABLE)) {
                getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
            }
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.PICKAXE_MINEABLE)) {
                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
            }
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.SHOVEL_MINEABLE)) {
                getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(block);
            }
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.HOE_MINEABLE)) {
                getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(block);
            }

            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
            }
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.NEEDS_IRON_TOOL)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
            }
            if (JAAVAABlocks.getParent(block).getDefaultState().isIn(BlockTags.NEEDS_STONE_TOOL)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
            }

            if (key.contains("glass")) {
                getOrCreateTagBuilder(COMMON_GLASS).add(block);
                getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(block);
            }
            if (key.contains("ice")) {
                getOrCreateTagBuilder(BlockTags.ICE).add(block);
            }
            if (key.contains("obsidian")) {
                getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(block);
            }
            if (key.contains("ore")) {
                if (key.contains("coal")) {
                    getOrCreateTagBuilder(BlockTags.COAL_ORES).add(block);
                }
                if (key.contains("copper")) {
                    getOrCreateTagBuilder(BlockTags.COPPER_ORES).add(block);
                }
                if (key.contains("diamond")) {
                    getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(block);
                }
                if (key.contains("emerald")) {
                    getOrCreateTagBuilder(BlockTags.EMERALD_ORES).add(block);
                }
                if (key.contains("gold")) {
                    getOrCreateTagBuilder(BlockTags.GOLD_ORES).add(block);
                }
                if (key.contains("iron")) {
                    getOrCreateTagBuilder(BlockTags.IRON_ORES).add(block);
                }
                if (key.contains("lapis")) {
                    getOrCreateTagBuilder(BlockTags.LAPIS_ORES).add(block);
                }
                if (key.contains("redstone")) {
                    getOrCreateTagBuilder(BlockTags.REDSTONE_ORES).add(block);
                }
            }
            if (key.contains("planks")) {
                getOrCreateTagBuilder(BlockTags.PLANKS).add(block);
            }
            if (key.contains("reinforced_deepslate")) {
                getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(block);
                getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE).add(block);
            }
            if (key.contains("snow")) {
                getOrCreateTagBuilder(BlockTags.SNOW).add(block);
            }
            if (key.contains("soul")) {
                getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(block);
                getOrCreateTagBuilder(BlockTags.SOUL_SPEED_BLOCKS).add(block);
            }
            if (key.contains("stone_brick")) {
                getOrCreateTagBuilder(BlockTags.STONE_BRICKS).add(block);
            }
            if (key.contains("terracotta")) {
                getOrCreateTagBuilder(BlockTags.TERRACOTTA).add(block);
            }
            if (key.contains("wart")) {
                getOrCreateTagBuilder(BlockTags.WART_BLOCKS).add(block);
            }
            if (key.contains("wool")) {
                getOrCreateTagBuilder(BlockTags.WOOL).add(block);
            }
        }
    }
    @Override
    public TagKey<Block> registerTagKey(String namespace, String name) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, name));
    }
}
