package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.loot.v2.*;
import net.gordyjack.jaavaa.item.*;
import net.minecraft.entity.*;
import net.minecraft.loot.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.*;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.*;
import net.minecraft.registry.*;

public class JAAVAALootTableModifiers {
    private static final RegistryKey<LootTable> ALLAY_KEY = EntityType.ALLAY.getLootTableId();
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (key.equals(ALLAY_KEY)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .bonusRolls(ConstantLootNumberProvider.create(1.0f))
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .conditionally(RandomChanceWithLootingLootCondition.builder(0.3f, 0.1f))
                        .with(ItemEntry.builder(JAAVAAItems.ALLAY_ESSENCE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)));
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
