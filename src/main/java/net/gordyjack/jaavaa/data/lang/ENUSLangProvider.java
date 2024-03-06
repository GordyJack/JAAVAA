package net.gordyjack.jaavaa.data.lang;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.items.*;
import net.minecraft.item.*;
import org.apache.commons.lang3.text.*;

import java.nio.file.*;

public class ENUSLangProvider
extends FabricLanguageProvider{
    public ENUSLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for(Item item : ModItems.ITEMS) {
            translationBuilder.add(item, getTranslatedName(item));
        }
        
        try {
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/jaavaa/lang/en_us.existing.json").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }
    private String getTranslatedName(ItemConvertible itemConvertible) {
        return getTranslatedName(itemConvertible.asItem().getTranslationKey());
    }
    @SuppressWarnings("deprecation")
    private String getTranslatedName(String name) {
        name = name.substring(name.lastIndexOf('.') + 1);
        name = name.replace('_', ' ');
        name = WordUtils.capitalizeFully(name);
        if (name.equals("Jaavaa"))
            name = name.toUpperCase();
        return name;
    }
}
