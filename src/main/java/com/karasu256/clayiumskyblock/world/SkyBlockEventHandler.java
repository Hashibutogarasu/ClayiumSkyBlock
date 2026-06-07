package com.karasu256.clayiumskyblock.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.karasu256.clayiumskyblock.block.BlockOreGenerator;
import com.karasu256.clayiumskyblock.block.OreType;
import com.karasu256.clayiumskyblock.block.TileEntityOreGenerator;
import com.karasu256.clayiumskyblock.registry.ModBlocks;
import com.karasu256.clayiumskyblock.registry.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles world-lifecycle events and ore generator block interactions.
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

    /**
     * Intercepts block-break attempts on the ore generator. If the player's pickaxe tier is
     * sufficient, the break is cancelled, ore items are dropped, and the block stays in place.
     * Otherwise the break is silently cancelled.
     *
     * @param event the block break event
     */
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (ModBlocks.blockOreGenerator == null) {
            return;
        }
        if (event.block != ModBlocks.blockOreGenerator) {
            return;
        }
        if (event.world.isRemote) {
            return;
        }

        int tier = event.blockMetadata;
        EntityPlayer player = event.getPlayer();
        ItemStack held = player.getHeldItem();

        if (!BlockOreGenerator.canHarvestTier(held, tier)) {
            return;
        }

        event.setCanceled(true);

        TileEntityOreGenerator te = (TileEntityOreGenerator) event.world.getTileEntity(event.x, event.y, event.z);
        if (te == null) {
            return;
        }

        OreType ore = te.getOreType();
        Block oreBlock = ore.getBlock();
        if (oreBlock != null) {
            for (ItemStack drop : oreBlock.getDrops(event.world, 0, 0, 0, ore.meta, 0)) {
                EntityItem entity = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, drop);
                event.world.spawnEntityInWorld(entity);
            }
            event.world.playSoundEffect(
                event.x + 0.5,
                event.y + 0.5,
                event.z + 0.5,
                oreBlock.stepSound.getBreakSound(),
                1.0f,
                1.0f);
        }

        te.setOreType(OreType.getRandomOfTier(tier, event.world.rand));

        if (event.world.rand.nextInt(10000) == 0) {
            ItemStack bonus = event.world.rand.nextBoolean() ? new ItemStack(ModItems.tierUpgrade)
                : new ItemStack(ModItems.tierDowngrade);
            event.world
                .spawnEntityInWorld(new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, bonus));
        }

        held.damageItem(1, player);
        if (held.stackSize <= 0) {
            player.setCurrentItemOrArmor(0, null);
        }
    }
}
