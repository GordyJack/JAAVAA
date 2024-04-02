package net.gordyjack.jaavaa.item.custom;

import net.minecraft.item.*;

public class GlintingItem extends Item {
    public GlintingItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
