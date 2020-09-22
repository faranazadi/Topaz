package com.topaz.net.packet.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class PlayerInactivePacketListener implements PacketExecutor {

    //CALLED EVERY 3 MINUTES OF INACTIVITY

    @Override
    public void execute(Player player, Packet packet) {
    }
}
