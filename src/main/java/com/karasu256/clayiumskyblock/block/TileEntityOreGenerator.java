package com.karasu256.clayiumskyblock.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Tile entity for the ore generator block. Stores the ore type index that determines what the
 * block drops when mined.
 */
public class TileEntityOreGenerator extends TileEntity {

    private int oreTypeIndex = OreType.CLAY.ordinal();

    /**
     * Returns the current ore type.
     *
     * @return the current {@link OreType}
     */
    public OreType getOreType() {
        OreType[] values = OreType.values();
        if (oreTypeIndex < 0 || oreTypeIndex >= values.length) {
            return OreType.CLAY;
        }
        return values[oreTypeIndex];
    }

    /**
     * Sets the ore type and marks the block for a client update.
     *
     * @param type the new ore type
     */
    public void setOreType(OreType type) {
        this.oreTypeIndex = type.ordinal();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        oreTypeIndex = compound.getInteger("OreTypeIndex");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("OreTypeIndex", oreTypeIndex);
    }
}
