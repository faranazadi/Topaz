package com.topaz.net.packet.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class CloseInterfacePacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        player.getPacketSender().sendInterfaceRemoval();
    }
}
