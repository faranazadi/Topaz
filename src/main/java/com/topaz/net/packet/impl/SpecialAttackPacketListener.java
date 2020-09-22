package com.topaz.net.packet.impl;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

/**
 * This packet listener handles the action when pressing
 * a special attack bar.
 *
 * @author Professor Oak
 */

public class SpecialAttackPacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        @SuppressWarnings("unused")
        int specialBarButton = packet.readInt();

        if (player.getHitpoints() <= 0) {
            return;
        }

        CombatSpecial.activate(player);
    }
}
