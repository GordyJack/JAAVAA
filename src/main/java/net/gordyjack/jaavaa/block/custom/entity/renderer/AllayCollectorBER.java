package net.gordyjack.jaavaa.block.custom.entity.renderer;

import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavaa.block.custom.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class AllayCollectorBER
implements BlockEntityRenderer<AllayCollectorEntity>{
    private final EntityRenderDispatcher entityRenderDispatcher;
    private static AllayEntity allayEntity;
    public AllayCollectorBER(BlockEntityRendererFactory.Context ctx) {
        this.entityRenderDispatcher = ctx.getEntityRenderDispatcher();
    }
    @Override
    public void render(AllayCollectorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return;
        }
        if (allayEntity == null) {
            allayEntity = new AllayEntity(EntityType.ALLAY, world);
        }

        matrices.push();
        BlockState state = entity.getCachedState();
        if (state == null) {
            return;
        }

        final float SCALE_FACTOR = 0.95f;
        final float DIRECTIONAL_ADJUSTMENT = 0.2f;
        Direction facing = state.get(CollectorBlock.FACING);
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
        switch(facing) {
            case DOWN -> {
                matrices.translate(0.0f, -DIRECTIONAL_ADJUSTMENT, -DIRECTIONAL_ADJUSTMENT);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            }
            case UP -> {
                matrices.translate(0.0f, DIRECTIONAL_ADJUSTMENT, DIRECTIONAL_ADJUSTMENT);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            }
            case NORTH -> {
                matrices.translate(0.0f, -DIRECTIONAL_ADJUSTMENT, -DIRECTIONAL_ADJUSTMENT);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            }
            case SOUTH -> {
                matrices.translate(0.0f, -DIRECTIONAL_ADJUSTMENT, DIRECTIONAL_ADJUSTMENT);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            }
            case WEST -> {
                matrices.translate(-DIRECTIONAL_ADJUSTMENT, -DIRECTIONAL_ADJUSTMENT, 0.0f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            }
            case EAST -> {
                matrices.translate(DIRECTIONAL_ADJUSTMENT, -DIRECTIONAL_ADJUSTMENT, 0.0f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            }
        }
        this.entityRenderDispatcher.render(allayEntity, 0.0, 0.0, 0.0, 0.0f, 0, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
