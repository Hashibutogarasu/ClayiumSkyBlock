package com.karasu256.clayiumskyblock.world;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * A chunk provider that generates completely empty chunks with no terrain, bedrock, or structures.
 */
public class VoidChunkProvider implements IChunkProvider {

    private final World world;

    /**
     * Constructs a VoidChunkProvider for the given world.
     *
     * @param world the world this provider belongs to
     */
    public VoidChunkProvider(World world) {
        this.world = world;
    }

    @Override
    public boolean chunkExists(int x, int z) {
        return true;
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        Chunk chunk = new Chunk(world, x, z);
        Arrays.fill(chunk.getBiomeArray(), (byte) BiomeGenBase.plains.biomeID);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return provideChunk(x, z);
    }

    @Override
    public void populate(IChunkProvider provider, int x, int z) {}

    @Override
    public void saveExtraData() {}

    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate progress) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "VoidChunkProvider";
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        return null;
    }

    @Override
    public ChunkPosition func_147416_a(World world, String structureName, int x, int y, int z) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int x, int z) {}
}
