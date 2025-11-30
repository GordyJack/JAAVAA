package net.gordyjack.jaavaa.item;

import net.gordyjack.jaavaa.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;

import java.util.*;

import static net.gordyjack.jaavaa.block.JAAVAABlocks.*;

public final class JAAVAAItems {
    private JAAVAAItems() {}

    public static void init() {
        JAAVAA.log("Initializing JAAVAA Items", 'd');
        CreativeTabs.init();
    }

    public static final class CreativeTabs {
        private CreativeTabs() {}

        public static final ArrayList<ResourceKey<CreativeModeTab>> CREATIVE_MODE_TABS = new ArrayList<>();

        //Methods
        /**
         * Registers an ItemGroup with the given name and icon.
         * @param name The name of the ItemGroup
         * @param icon The icon of the ItemGroup
         * @return The registered ItemGroup
         */
        private static ResourceKey<CreativeModeTab> registerCreativeModeTab(String name, ItemLike icon, int column, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
            ResourceKey<CreativeModeTab> returnKey = ResourceKey.create(Registries.CREATIVE_MODE_TAB, JAAVAA.id(column + "_" + name));
            CreativeModeTab.Row row = Math.floor((double) 5 / column) % 2 != 0 ? CreativeModeTab.Row.TOP : CreativeModeTab.Row.BOTTOM;
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, returnKey,
                    CreativeModeTab.builder(row, 6 + column)
                            .title(Component.translatable("creativeTab.jaavaa." + column + "_" + name))
                            .icon(() -> new ItemStack(icon))
                            .displayItems(displayItemsGenerator)
                            .build());
            CREATIVE_MODE_TABS.add(returnKey);
            return returnKey;
        }

        private static void init() {
            JAAVAA.log("Initializing JAAVAA Item Groups", 'd');
            ResourceKey<CreativeModeTab> JAAVAA_BEAMS =
                    registerCreativeModeTab("jaavaa_beams", STONE_SET.beam(), 3,
                            (displayContext, entries) -> {
                                for (Block parent : BLOCK_PIECE_SETS.keySet()) {
                                    entries.accept(BLOCK_PIECE_SETS.get(parent).beam());
                                }
                            });
            ResourceKey<CreativeModeTab> JAAVAA_BLOCKTANTS =
                    registerCreativeModeTab("jaavaa_blocktants", STONE_SET.blocktant(), 4,
                            (displayContext, entries) -> {
                                for (Block parent : BLOCK_PIECE_SETS.keySet()) {
                                    entries.accept(BLOCK_PIECE_SETS.get(parent).blocktant());
                                }
                            });
            ResourceKey<CreativeModeTab> JAAVAA_PANELS =
                    registerCreativeModeTab("jaavaa_panels", STONE_SET.panel(), 5,
                            (displayContext, entries) -> {
                                for (Block parent : BLOCK_PIECE_SETS.keySet()) {
                                    entries.accept(BLOCK_PIECE_SETS.get(parent).panel());
                                }
                            });
        }
    }
}
