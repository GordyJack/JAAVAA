package net.gordyjack.jaavaa.data.tags;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.mininglevel.v1.*;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.concurrent.*;

public class ModBlockTagGenerator
extends FabricTagProvider.BlockTagProvider
implements ModTagInterface<Block>{
    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public final TagKey<Block> COMMON_GLASS = registerCommonTagKey("glass_blocks");
    public final TagKey<Block> MINECRAFT_IMPERMEABLE = BlockTags.IMPERMEABLE;
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for(Block block : ModBlocks.BLOCKS) {
            String key = block.getTranslationKey();
            boolean isGlass = key.contains("glass");
            if (isGlass) {
                getOrCreateTagBuilder(COMMON_GLASS).add(block);
                getOrCreateTagBuilder(MINECRAFT_IMPERMEABLE).add(block);
            }
        }
    }
    @Override
    public TagKey<Block> registerTagKey(String namespace, String name) {
        return TagKey.of(this.registryRef, new Identifier(namespace, name));
    }
}
