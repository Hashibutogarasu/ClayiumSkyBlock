package com.karasu256.clayiumskyblock.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.karasu256.clayiumskyblock.registry.ModCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The ore generator block. Metadata encodes the tier (0=stone, 1=iron, 2=diamond). The ore type
 * is stored in a {@link TileEntityOreGenerator}. Textures are borrowed from the source ore blocks.
 */
public class BlockOreGenerator extends Block implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    private IIcon[] oreIcons;

    /** Default icons per tier shown in the inventory and as fallback. */
    @SideOnly(Side.CLIENT)
    private IIcon[] tierIcons;

    /** Creates the ore generator block. */
    public BlockOreGenerator() {
        super(Material.rock);
        setHarvestLevel("pickaxe", 0);
        setBlockName("oreGenerator");
        setResistance(5.0f);
        setHardness(3.0f);
        setCreativeTab(ModCreativeTabs.TAB);
    }

    /**
     * Returns whether the given item stack can harvest this block at the specified tier.
     *
     * @param held the held item stack, or {@code null} if the player has no item
     * @param tier the block tier (0=stone, 1=iron, 2=diamond)
     * @return {@code true} if the pickaxe harvest level meets or exceeds {@code tier + 1}
     */
    public static boolean canHarvestTier(ItemStack held, int tier) {
        if (held == null) {
            return false;
        }
        int level = held.getItem()
            .getHarvestLevel(held, "pickaxe");
        return level >= tier + 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        TileEntityOreGenerator te = new TileEntityOreGenerator();
        te.setOreType(OreType.getRandomOfTier(meta, world.rand));
        return te;
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        OreType[] types = OreType.values();
        oreIcons = new IIcon[types.length];
        for (int i = 0; i < types.length; i++) {
            try {
                oreIcons[i] = register.registerIcon(types[i].iconName);
            } catch (Exception e) {
                oreIcons[i] = register.registerIcon("minecraft:coal_ore");
            }
        }
        tierIcons = new IIcon[3];
        tierIcons[0] = oreIcons[OreType.COAL_ORE.ordinal()];
        tierIcons[1] = oreIcons[OreType.DIAMOND_ORE.ordinal()];
        tierIcons[2] = oreIcons[OreType.EMERALD_ORE.ordinal()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int tier = meta < 3 ? meta : 0;
        return tierIcons[tier];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityOreGenerator) {
            OreType ore = ((TileEntityOreGenerator) te).getOreType();
            int idx = ore.ordinal();
            if (idx >= 0 && idx < oreIcons.length && oreIcons[idx] != null) {
                return oreIcons[idx];
            }
        }
        return getIcon(side, world.getBlockMetadata(x, y, z));
    }
}
