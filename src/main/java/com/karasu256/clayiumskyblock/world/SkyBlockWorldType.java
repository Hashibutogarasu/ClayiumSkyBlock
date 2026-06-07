package com.karasu256.clayiumskyblock.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * A world type that generates an empty world into which the Sky Block island is later placed by
 * {@link SkyBlockWorldGenerator}.
 */
public class SkyBlockWorldType extends WorldType {

    /** Singleton instance registered with Forge's world type list. */
    public static final SkyBlockWorldType INSTANCE = new SkyBlockWorldType();

    private SkyBlockWorldType() {
        super("SKY_BLOCK");
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
