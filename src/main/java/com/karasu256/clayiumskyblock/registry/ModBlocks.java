package com.karasu256.clayiumskyblock.registry;

import com.karasu256.clayiumskyblock.block.BlockOreGenerator;
import com.karasu256.clayiumskyblock.block.TileEntityOreGenerator;
import com.karasu256.clayiumskyblock.item.ItemBlockOreGenerator;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Registers all blocks added by this mod.
 */
public final class ModBlocks {

    /** The ore generator block. */
    public static BlockOreGenerator blockOreGenerator;

    private ModBlocks() {}

    /**
     * Registers all mod blocks and their tile entities with the GameRegistry.
     */
    public static void register() {
        blockOreGenerator = new BlockOreGenerator();
        GameRegistry.registerBlock(blockOreGenerator, ItemBlockOreGenerator.class, "oreGenerator");
        GameRegistry.registerTileEntity(TileEntityOreGenerator.class, "clayiumskyblock.oreGenerator");
    }
}
