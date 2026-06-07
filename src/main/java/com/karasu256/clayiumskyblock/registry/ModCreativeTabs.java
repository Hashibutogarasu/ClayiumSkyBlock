package com.karasu256.clayiumskyblock.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Creative tabs added by this mod.
 */
public final class ModCreativeTabs {

    /**
     * The single creative tab that groups all items and blocks added by this mod.
     */
    public static final CreativeTabs TAB = new CreativeTabs("clayiumskyblock") {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.blockOreGenerator);
        }
    };

    private ModCreativeTabs() {}
}
