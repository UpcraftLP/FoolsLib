package com.github.upcraftlp.foolslib.client;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.config.FoolsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class FoolsConfigGui extends GuiConfig {

    public FoolsConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), FoolsLib.MODID, false, false, I18n.format("foolslib.config.title"));
    }

    private static List<IConfigElement> getConfigElements() {
        final Configuration configuration = FoolsConfig.config;
        final ConfigCategory topLevelCategory = configuration.getCategory(FoolsConfig.CATEGORY_GENERAL);
        topLevelCategory.getChildren().forEach(category -> category.setLanguageKey("foolslib.config." + category.getName()));
        return new ConfigElement(topLevelCategory).getChildElements();
    }

    public static class Factory implements IModGuiFactory {

        @Override
        public void initialize(Minecraft minecraftInstance) {
            //NO-OP
        }

        @Override
        public Class<? extends GuiScreen> mainConfigGuiClass() {
            return FoolsConfigGui.class;
        }

        @Override
        public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
            return null;
        }

        @Override
        public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
            return null;
        }
    }
}
