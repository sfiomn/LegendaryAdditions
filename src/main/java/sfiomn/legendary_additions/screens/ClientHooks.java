package sfiomn.legendary_additions.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import sfiomn.legendary_additions.tileentities.AbstractDungeonHeartTileEntity;

public class ClientHooks {
    public static void openDungeonHeartScreen(TileEntity tileEntity) {
        if (tileEntity instanceof AbstractDungeonHeartTileEntity) {
            AbstractDungeonHeartTileEntity te = (AbstractDungeonHeartTileEntity) tileEntity;
            Minecraft.getInstance().setScreen(new DungeonHeartScreen(te));
        } else {
            throw new IllegalStateException("Tile entity container is missing!");
        }
    }
}
