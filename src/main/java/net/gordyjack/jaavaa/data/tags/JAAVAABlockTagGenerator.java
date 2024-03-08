package net.gordyjack.jaavaa.data.tags;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

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
        for(Block block : JAAVAABlocks.BLOCKS) {
            String key = block.getTranslationKey();
            boolean isGlass = key.contains("glass");
            if (isGlass) {
                getOrCreateTagBuilder(COMMON_GLASS).add(block);
                getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(block);
            }
        }
        for(WallBlock wallBlock : JAAVAABlocks.WALLS) {
            getOrCreateTagBuilder(BlockTags.WALLS).add(wallBlock);
        }
    }
    @Override
    public TagKey<Block> registerTagKey(String namespace, String name) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, name));
    }
}
