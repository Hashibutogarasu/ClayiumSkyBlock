package com.karasu256.clayiumskyblock;

import net.minecraftforge.common.MinecraftForge;

import com.karasu256.clayiumskyblock.block.OreType;
import com.karasu256.clayiumskyblock.recipe.RecipeTierDowngrade;
import com.karasu256.clayiumskyblock.recipe.RecipeTierUpgrade;
import com.karasu256.clayiumskyblock.registry.ModBlocks;
import com.karasu256.clayiumskyblock.registry.ModItems;
import com.karasu256.clayiumskyblock.world.SkyBlockEventHandler;
import com.karasu256.clayiumskyblock.world.SkyBlockWorldGenerator;
import com.karasu256.clayiumskyblock.world.SkyBlockWorldType;
import com.karasu256.clayiumskyblock.world.VoidWorldType;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

/** Common (server-side) proxy handling mod lifecycle events. */
public class CommonProxy {

    /**
     * Runs before mod initialization; loads config and registers world types.
     *
     * @param event the pre-initialization event
     */
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());
        @SuppressWarnings("unused")
        Object init = VoidWorldType.INSTANCE;
        @SuppressWarnings("unused")
        Object init2 = SkyBlockWorldType.INSTANCE;
        ModBlocks.register();
        ModItems.register();
    }

    /**
     * Runs during mod initialization; registers world generators.
     *
     * @param event the initialization event
     */
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new SkyBlockWorldGenerator(), 0);
        MinecraftForge.EVENT_BUS.register(new SkyBlockEventHandler());
        GameRegistry.addRecipe(new RecipeTierUpgrade());
        GameRegistry.addRecipe(new RecipeTierDowngrade());
    }

    /**
     * Runs after mod initialization.
     *
     * @param event the post-initialization event
     */
    public void postInit(FMLPostInitializationEvent event) {
        OreType.resolveBlocks();
    }

    /**
     * Runs when the server is starting.
     *
     * @param event the server starting event
     */
    public void serverStarting(FMLServerStartingEvent event) {}
}
