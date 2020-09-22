package com.topaz.net.packet.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.PlayerRelations.PrivateChatStatus;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class ChatSettingsPacketListener implements PacketExecutor {


    @Override
    public void execute(Player player, Packet packet) {
        @SuppressWarnings("unused")
        int publicMode = packet.readByte();

        int privateMode = packet.readByte();

        @SuppressWarnings("unused")
        int tradeMode = packet.readByte();

		/*
		 * Did the player change their private chat status? 
		 * If yes, update status for all friends.
		 */

        if (privateMode > PrivateChatStatus.values().length) {
            return;
        }

        PrivateChatStatus privateChatStatus = PrivateChatStatus.values()[privateMode];
        if (player.getRelations().getStatus() != privateChatStatus) {
            player.getRelations().setStatus(privateChatStatus, true);
        }
    }
}
