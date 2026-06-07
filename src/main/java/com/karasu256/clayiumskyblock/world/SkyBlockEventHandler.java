package com.karasu256.clayiumskyblock.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles world-lifecycle events for Sky Block worlds.
 *
 * <p>
 * Overrides spawn-point creation so the player always starts on the island before any chunks
 * are populated.
 */
public class SkyBlockEventHandler {

    /**
     * Sets the world spawn to a random position on the island surface when a Sky Block world is
     * first created.
     *
     * <p>
     * Excluded cells: the 3×3 area covered by the oak tree's leaf canopy (x/z in [-1,1]), and
     * the chest at (2, 2). The remaining 15 platform cells are all valid candidates.
     *
     * @param event the spawn-position creation event
     */
    @SubscribeEvent
    public void onCreateSpawnPosition(WorldEvent.CreateSpawnPosition event) {
        if (event.world.getWorldInfo()
            .getTerrainType() != SkyBlockWorldType.INSTANCE) {
            return;
        }

        List<int[]> candidates = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (x >= -1 && x <= 1 && z >= -1 && z <= 1) continue;
                if (x == 2 && z == 2) continue;
                candidates.add(new int[] { x, z });
            }
        }

        int[] chosen = candidates.get(event.world.rand.nextInt(candidates.size()));
        event.world.setSpawnLocation(chosen[0], 62, chosen[1]);
        event.setCanceled(true);
    }
}
