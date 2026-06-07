package com.karasu256.clayiumskyblock.recipe;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.karasu256.clayiumskyblock.registry.ModItems;

/**
 * Crafting recipe that produces a tier upgrade item when all 9 crafting slots are filled with 9
 * distinct non-block items (ore drops such as coal, diamond, lapis, etc.).
 */
public class RecipeTierUpgrade implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack == null || stack.getItem() instanceof ItemBlock) {
                return false;
            }
            String key = Item.getIdFromItem(stack.getItem()) + ":" + stack.getItemDamage();
            seen.add(key);
        }
        return seen.size() == 9;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        return new ItemStack(ModItems.tierUpgrade);
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.tierUpgrade);
    }
}
