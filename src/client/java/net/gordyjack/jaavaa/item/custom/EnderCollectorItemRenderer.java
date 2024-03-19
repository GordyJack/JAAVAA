package net.gordyjack.jaavaa.item.custom;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.gordyjack.jaavaa.item.JAAVAAItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class EnderCollectorItemRenderer
implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final ItemStack EMPTY_COLLECTOR = new ItemStack(JAAVAAItems.EMPTY_ENDER_COLLECTOR);
    private static final ItemStack EMPTY_COLLECTOR_GLINTING = new ItemStack(JAAVAAItems.EMPTY_ENDER_COLLECTOR_GLINTING);
    private static EndermanEntity endermanEntity;
    public EnderCollectorItemRenderer() {
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        EntityRenderDispatcher rendererDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        World world = MinecraftClient.getInstance().world;
        if (world == null) {
            return;
        }
        if (endermanEntity == null) {
            endermanEntity = new EndermanEntity(EntityType.ENDERMAN, world);
        }

        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        if(mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        }
        if(mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        }
        if(stack.hasGlint()) {
            itemRenderer.renderItem(EMPTY_COLLECTOR_GLINTING, mode, light, overlay, matrices, vertexConsumers, world, 0);
        } else {
            itemRenderer.renderItem(EMPTY_COLLECTOR, mode, light, overlay, matrices, vertexConsumers, world, 0);
        }
        matrices.pop();

        //Rendering
        //TODO: Fix this
        matrices.push();
        switch (mode) {
            case GROUND -> {
                matrices.translate(0.5f, 0.0f, 0.57f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
            case FIXED -> {
                matrices.translate(0.5f, 0.0f, 0.35f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
            case THIRD_PERSON_LEFT_HAND -> {
                matrices.translate(.55f, 0.0f, 0.475f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(75));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
            case THIRD_PERSON_RIGHT_HAND -> {
                matrices.translate(0.45f, 0.0f, 0.475f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(75));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                matrices.translate(0.5f, 0.0f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND ? -45 : 45));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
            case GUI -> {
                matrices.translate(0.5f, 0.0f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
                matrices.scale(0.3f, 0.3f, 0.3f);
            }
        }
        rendererDispatcher.render(endermanEntity, 0.0, 0.0, 0.0, 0.0f, 0, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
