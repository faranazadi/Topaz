package com.topaz.net.packet.impl;

import com.topaz.game.content.clan.ClanChatManager;
import com.topaz.game.content.presets.Presetables;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.container.impl.Bank;
import com.topaz.game.model.teleportation.TeleportHandler;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class InterfaceActionClickOpcode implements PacketExecutor {

	@Override
	public void execute(Player player, Packet packet) {
		int interfaceId = packet.readInt();
		int action = packet.readByte();

		if (player == null || player.getHitpoints() <= 0
				|| player.isTeleporting()) {
			return;
		}

		if (Bank.handleButton(player, interfaceId, action)) {
			return;
		}
		
		if (ClanChatManager.handleButton(player, interfaceId, action)) {
			return;
		}
		
		if (Presetables.handleButton(player, interfaceId)) {
			return;
		}
		
		if (TeleportHandler.handleButton(player, interfaceId, action)) {
			return;
		}
	}
}
