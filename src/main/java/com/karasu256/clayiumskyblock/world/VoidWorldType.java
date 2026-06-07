package com.karasu256.clayiumskyblock.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * A world type that generates a completely empty world with no terrain.
 */
public class VoidWorldType extends WorldType {

    /** Singleton instance registered with Forge's world type list. */
    public static final VoidWorldType INSTANCE = new VoidWorldType();

    private VoidWorldType() {
        super("THE_VOID");
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new VoidChunkProvider(world);
    }

    @Override
    public int getSpawnFuzz() {
        return 1;
    }
}
