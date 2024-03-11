package net.gordyjack.jaavaa.block.custom.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class JAAVAABlockEntityTypes {
    public static final BlockEntityType<AllayCollectorEntity> ALLAY_COLLECTOR = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            JAAVAA.getID("allay_collector"), FabricBlockEntityTypeBuilder.create(AllayCollectorEntity::new, JAAVAABlocks.ALLAY_COLLECTOR).build());

    public static void registerBlockEntityTypes() {
        JAAVAA.logInfo("Registering block entity types");
    }
}
