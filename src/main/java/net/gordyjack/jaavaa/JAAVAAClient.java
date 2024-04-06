package net.gordyjack.jaavaa;

import net.fabricmc.api.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.block.custom.entity.*;
import net.gordyjack.jaavaa.block.custom.entity.renderer.*;
import net.gordyjack.jaavaa.item.*;
import net.gordyjack.jaavaa.item.custom.renderer.*;
import net.minecraft.block.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;

public class JAAVAAClient
implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        configureBlockRenderLayerMap();

        BlockEntityRendererFactories.register(JAAVAABlockEntityTypes.ALLAY_COLLECTOR, AllayCollectorBER::new);
        BuiltinItemRendererRegistry.INSTANCE.register(JAAVAAItems.PERSONAL_ALLAY_COLLECTOR, new AllayCollectorItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(JAAVAAItems.PERSONAL_ENDER_COLLECTOR, new EnderCollectorItemRenderer());
    }
    private void configureBlockRenderLayerMap() {
        BlockRenderLayerMap.INSTANCE.putBlock(JAAVAABlocks.ALLAY_COLLECTOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JAAVAABlocks.ENDER_COLLECTOR, RenderLayer.getTranslucent());
        for (Block block : JAAVAABlocks.BLOCKS) {
            String key = block.getTranslationKey();
            boolean isGlass = key.contains("glass");
            boolean isSlime = key.contains("slime");
            boolean isHoney = key.contains("honey_");
            boolean isLeaves = key.contains("leaves");
            if (isGlass || isSlime || isHoney) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
            }
            if (isLeaves) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            }
        }
    }
}
