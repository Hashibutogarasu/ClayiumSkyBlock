package com.karasu256.clayiumskyblock.registry;

import com.karasu256.clayiumskyblock.item.ItemTierDowngrade;
import com.karasu256.clayiumskyblock.item.ItemTierUpgrade;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Registers all items added by this mod.
 */
public final class ModItems {

    /** Item that upgrades the tier of an ore generator block. */
    public static ItemTierUpgrade tierUpgrade;

    /** Item that downgrades the tier of an ore generator block. */
    public static ItemTierDowngrade tierDowngrade;

    private ModItems() {}

    /**
     * Registers all mod items with the GameRegistry.
     */
    public static void register() {
        tierUpgrade = new ItemTierUpgrade();
        GameRegistry.registerItem(tierUpgrade, "tierUpgrade");

        tierDowngrade = new ItemTierDowngrade();
        GameRegistry.registerItem(tierDowngrade, "tierDowngrade");
    }
}
