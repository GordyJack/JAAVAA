package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.gordyjack.jaavaa.JAAVAA;
import net.gordyjack.jaavaa.item.JAAVAAItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class JAAVAALootTableModifiers {
    private static final Identifier ALLAY_ID = JAAVAA.getID("minecraft", "entities/allay");
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (id.equals(ALLAY_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .bonusRolls(ConstantLootNumberProvider.create(1.0f))
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .conditionally(RandomChanceWithLootingLootCondition.builder(0.3f, 0.1f))
                        .with(ItemEntry.builder(JAAVAAItems.ALLAY_ESSENCE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)));
                supplier.pool(poolBuilder.build());
            }
        });
    }
}
