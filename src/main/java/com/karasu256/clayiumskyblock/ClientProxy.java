package com.karasu256.clayiumskyblock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

/** Client-side proxy; registers client-only resources such as localization entries. */
public class ClientProxy extends CommonProxy {

    /**
     * Loads all translations from the mod's lang files and registers them with {@link
     * LanguageRegistry}.
     *
     * @param event the initialization event
     */
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        loadLang("en_US");
        loadLang("ja_JP");
    }

    /**
     * Reads the specified lang file from the mod's assets and registers each key-value pair with
     * {@link LanguageRegistry}.
     *
     * @param locale the locale code, e.g. {@code "en_US"} or {@code "ja_JP"}
     */
    private void loadLang(String locale) {
        String path = "/assets/" + ClayiumSkyBlock.MODID + "/lang/" + locale + ".lang";
        InputStream is = ClientProxy.class.getResourceAsStream(path);
        if (is == null) {
            return;
        }
        LanguageRegistry lang = LanguageRegistry.instance();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int sep = line.indexOf('=');
                if (sep < 1) {
                    continue;
                }
                String key = line.substring(0, sep)
                    .trim();
                String value = line.substring(sep + 1);
                lang.addStringLocalization(key, locale, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
