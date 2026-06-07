package com.karasu256.clayiumskyblock;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

/** Client-side proxy; registers client-only resources such as localization entries. */
public class ClientProxy extends CommonProxy {

    /**
     * Registers translations for the mod's world types.
     *
     * @param event the initialization event
     */
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        LanguageRegistry lang = LanguageRegistry.instance();
        lang.addStringLocalization("generator.THE_VOID", "en_US", "The Void");
        lang.addStringLocalization("generator.THE_VOID", "ja_JP", "奈落");
        lang.addStringLocalization("generator.SKY_BLOCK", "en_US", "Sky Block");
        lang.addStringLocalization("generator.SKY_BLOCK", "ja_JP", "スカイブロック");
    }
}
