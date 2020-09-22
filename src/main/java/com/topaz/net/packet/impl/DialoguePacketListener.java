package com.topaz.net.packet.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

/**
 * Represents a packet used for handling dialogues. This specific packet
 * currently handles the action for clicking the "next" option during a
 * dialogue.
 *
 * @author Professor Oak
 */

public class DialoguePacketListener implements PacketExecutor {

	@Override
	public void execute(Player player, Packet packet) {
	    player.getDialogueManager().advance();
	}
}
