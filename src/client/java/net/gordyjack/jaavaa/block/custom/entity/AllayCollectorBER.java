package net.gordyjack.jaavaa.block.custom.entity;

import net.gordyjack.jaavaa.block.custom.CollectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

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
