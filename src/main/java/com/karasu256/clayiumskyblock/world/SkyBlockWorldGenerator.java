package com.karasu256.clayiumskyblock.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

import com.karasu256.clayiumskyblock.registry.ModBlocks;

import cpw.mods.fml.common.IWorldGenerator;

/**
 * Generates the Sky Block starting island at chunk (0, 0).
 *
 * <p>
 * The island consists of a 5×5 dirt/grass platform centered at (0, 60, 0), a fixed-shape oak
 * tree at (0, 62, 0), and a chest containing a water bucket and a lava bucket. The tree has a
 * 4-block trunk (y=62–65) and a 3×3 leaf canopy at y=64–65 plus a cross at y=66.
 */
public class SkyBlockWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        if (chunkX != 0 || chunkZ != 0) {
            return;
        }
        WorldType worldType = world.getWorldInfo()
            .getTerrainType();
        if (worldType != SkyBlockWorldType.INSTANCE) {
            return;
        }
        generateIsland(world);
    }

    private void generateIsland(World world) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                setBlock(world, x, 60, z, Blocks.dirt);
                setBlock(world, x, 61, z, Blocks.grass);
            }
        }

        placeOakTree(world);

        setBlock(world, 2, 62, 2, Blocks.chest);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(2, 62, 2);
        if (chest != null) {
            chest.setInventorySlotContents(0, new ItemStack(Items.water_bucket));
            chest.setInventorySlotContents(1, new ItemStack(Items.lava_bucket));
            chest.setInventorySlotContents(2, new ItemStack(ModBlocks.blockOreGenerator));
        }
    }

    /**
     * Places a fixed-shape oak tree at (0, 62, 0).
     *
     * <p>
     * Trunk occupies y=62–65. Leaf canopy is a 3×3 square at y=64–65 and a plus-sign at y=66.
     * The shape is deterministic so the spawn exclusion zone in {@link SkyBlockEventHandler} can
     * reliably cover it.
     *
     * @param world the world to place the tree in
     */
    private void placeOakTree(World world) {
        for (int y = 62; y <= 65; y++) {
            setBlock(world, 0, y, 0, Blocks.log);
        }

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                placeLeaf(world, x, 64, z);
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                placeLeaf(world, x, 65, z);
            }
        }

        placeLeaf(world, 0, 66, 0);
        placeLeaf(world, 1, 66, 0);
        placeLeaf(world, -1, 66, 0);
        placeLeaf(world, 0, 66, 1);
        placeLeaf(world, 0, 66, -1);
    }

    private void placeLeaf(World world, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            world.setBlock(x, y, z, Blocks.leaves, 0, 2);
        }
    }

    private void setBlock(World world, int x, int y, int z, Block block) {
        world.setBlock(x, y, z, block, 0, 2);
    }
}
