package com.topaz.net.packet.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;


public class CreationMenuPacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        int itemId = packet.readInt();
        int amount = packet.readUnsignedByte();
        if (player.getCreationMenu() != null) {
            player.getCreationMenu().execute(itemId, amount);
        }
    }
}
