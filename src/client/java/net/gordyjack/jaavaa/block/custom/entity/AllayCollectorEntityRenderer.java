package net.gordyjack.jaavaa.block.custom.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

public class AllayCollectorEntityRenderer
implements BlockEntityRenderer<AllayCollectorEntity> {
    //TODO: All of this
    private final EntityRenderDispatcher entityRenderDispatcher;
    public AllayCollectorEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();
    }
    @Override
    public void render(AllayCollectorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }
}
