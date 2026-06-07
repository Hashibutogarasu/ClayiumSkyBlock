package com.karasu256.clayiumskyblock.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * ItemBlock for the ore generator block. Returns a tier-specific unlocalized name so that each
 * tier can have a distinct display name in the lang files.
 */
public class ItemBlockOreGenerator extends ItemBlock {

    /** Creates the item block for the given ore generator block. */
    public ItemBlockOreGenerator(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    /**
     * Returns a tier-suffixed unlocalized name so that {@code tile.oreGenerator.0.name},
     * {@code tile.oreGenerator.1.name}, and {@code tile.oreGenerator.2.name} are resolved.
     *
     * @param stack the item stack
     * @return the unlocalized name with tier suffix
     */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile.oreGenerator." + stack.getItemDamage();
    }
}
