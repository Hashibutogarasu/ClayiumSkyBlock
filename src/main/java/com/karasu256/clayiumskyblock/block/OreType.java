package com.karasu256.clayiumskyblock.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Defines all ore types supported by the ore generator, along with their tier, source block, and
 * texture icon name.
 */
public enum OreType {

    CLAY(0, "minecraft", "clay", 0, "minecraft:clay"),
    COAL_ORE(0, "minecraft", "coal_ore", 0, "minecraft:coal_ore"),
    IRON_ORE(0, "minecraft", "iron_ore", 0, "minecraft:iron_ore"),
    LAPIS_ORE(0, "minecraft", "lapis_ore", 0, "minecraft:lapis_ore"),
    QUARTZ_ORE(0, "minecraft", "quartz_ore", 0, "minecraft:quartz_ore"),
    CLAY_ORE(0, "clayium", "blockClayOre", 0, "clayium:clayore"),
    GOLD_ORE(1, "minecraft", "gold_ore", 0, "minecraft:gold_ore"),
    DIAMOND_ORE(1, "minecraft", "diamond_ore", 0, "minecraft:diamond_ore"),
    REDSTONE_ORE(1, "minecraft", "redstone_ore", 0, "minecraft:redstone_ore"),
    EMERALD_ORE(1, "minecraft", "emerald_ore", 0, "minecraft:emerald_ore"),
    DENSE_CLAY(1, "clayium", "blockClayOre", 1, "clayium:clayore-1");

    /** Tier: 0=stone, 1=iron, 2=diamond. */
    public final int tier;

    /** Mod ID of the source block. */
    public final String modId;

    /** Registry name of the source block. */
    public final String blockName;

    /** Metadata of the source block. */
    public final int meta;

    /** Icon name passed to {@code IIconRegister.registerIcon}. */
    public final String iconName;

    private Block resolvedBlock;

    OreType(int tier, String modId, String blockName, int meta, String iconName) {
        this.tier = tier;
        this.modId = modId;
        this.blockName = blockName;
        this.meta = meta;
        this.iconName = iconName;
    }

    /**
     * Resolves all block references from the GameRegistry. Must be called during or after
     * post-initialization.
     */
    public static void resolveBlocks() {
        for (OreType type : values()) {
            type.resolvedBlock = GameRegistry.findBlock(type.modId, type.blockName);
        }
    }

    /**
     * Returns the resolved source block, or {@code null} if the block could not be found.
     *
     * @return the source block
     */
    public Block getBlock() {
        return resolvedBlock;
    }

    /**
     * Returns a random {@link OreType} belonging to the given tier.
     *
     * @param tier   the target tier (0, 1, or 2)
     * @param random the random source
     * @return a randomly selected OreType of that tier, or {@link #CLAY} as fallback
     */
    public static OreType getRandomOfTier(int tier, Random random) {
        List<OreType> pool = new ArrayList<>();
        for (OreType type : values()) {
            if (type.tier <= tier) {
                pool.add(type);
            }
        }
        if (pool.isEmpty()) {
            return CLAY;
        }
        return pool.get(random.nextInt(pool.size()));
    }
}
