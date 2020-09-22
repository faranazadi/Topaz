package com.topaz.net.packet.impl;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.PlayerStatus;
import com.topaz.game.model.movement.WalkToAction;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;


public class TradeRequestPacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index > World.getPlayers().capacity() || index < 0) {
            return;
        }

        Player player2 = World.getPlayers().get(index);

        if (player == null
                || player.getHitpoints() <= 0
                || !player.isRegistered()
                || player2 == null || player2.getHitpoints() <= 0
                || !player2.isRegistered()) {
            player.getMovementQueue().reset();
            return;
        }

        player.setFollowing(player2);
        player.setWalkToTask(new WalkToAction(player) {
            @Override
            public void execute() {
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You cannot do that right now.");
                    return;
                }

                if (player2.busy()) {
                    String msg = "That player is currently busy.";

                    if (player2.getStatus() == PlayerStatus.TRADING) {
                        msg = "That player is currently trading with someone else.";
                    }

                    player.getPacketSender().sendMessage(msg);
                    return;
                }

                if (player.getArea() != null) {
                    if (!player.getArea().canTrade(player, player2)) {
                        player.getPacketSender().sendMessage("You cannot trade here.");
                        return;
                    }
                }

                if (player.getLocalPlayers().contains(player2)) {
                    player.getTrading().requestTrade(player2);
                }
            }

            @Override
            public boolean inDistance() {
                return player.calculateDistance(player2) == 1;
            }
        });

    }
}
