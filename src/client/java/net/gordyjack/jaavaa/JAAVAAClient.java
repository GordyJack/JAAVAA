package net.gordyjack.jaavaa;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.*;
import net.gordyjack.jaavaa.blocks.*;
import net.minecraft.block.*;
import net.minecraft.client.render.*;

public class JAAVAAClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		for (Block block : ModBlocks.BLOCKS) {
			String key = block.getTranslationKey();
			boolean isGlass = key.contains("glass");
			boolean isSlime = key.contains("slime");
			boolean isHoney = key.contains("honey_");
			if (isGlass || isSlime || isHoney) {
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());
			}
		}
	}
}