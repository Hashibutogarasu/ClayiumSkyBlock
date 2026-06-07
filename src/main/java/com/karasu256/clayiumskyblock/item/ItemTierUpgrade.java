package com.karasu256.clayiumskyblock.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.karasu256.clayiumskyblock.block.OreType;
import com.karasu256.clayiumskyblock.block.TileEntityOreGenerator;
import com.karasu256.clayiumskyblock.registry.ModBlocks;
import com.karasu256.clayiumskyblock.registry.ModCreativeTabs;

/**
 * When used on an ore generator block, upgrades its tier by one step (stone → iron → diamond).
 */
public class ItemTierUpgrade extends Item {

    /** Creates the tier upgrade item. */
    public ItemTierUpgrade() {
        setUnlocalizedName("tierUpgrade");
        setMaxStackSize(16);
        setCreativeTab(ModCreativeTabs.TAB);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (world.getBlock(x, y, z) != ModBlocks.blockOreGenerator) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        int currentTier = world.getBlockMetadata(x, y, z);
        if (currentTier >= 2) {
            return true;
        }
        int newTier = currentTier + 1;
        world.setBlockMetadataWithNotify(x, y, z, newTier, 3);
        TileEntityOreGenerator te = (TileEntityOreGenerator) world.getTileEntity(x, y, z);
        if (te != null) {
            te.setOreType(OreType.getRandomOfTier(newTier, world.rand));
        }
        stack.stackSize--;
        return true;
    }
}
