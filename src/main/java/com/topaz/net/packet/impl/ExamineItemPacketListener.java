package com.topaz.net.packet.impl;

import com.topaz.game.definition.ItemDefinition;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.container.impl.Bank;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;
import com.topaz.util.Misc;

public class ExamineItemPacketListener implements PacketExecutor {

	@Override
	public void execute(Player player, Packet packet) {
		int itemId = packet.readShort();
		int interfaceId = packet.readInt();

		// Coins
		if (itemId == 995 || itemId == 13307) {
			int amount = player.getInventory().getAmount(itemId);
			if (interfaceId >= Bank.CONTAINER_START && interfaceId < Bank.CONTAINER_START + Bank.TOTAL_BANK_TABS) {
				int fromBankTab = interfaceId - Bank.CONTAINER_START;
				amount = player.getBank(fromBankTab).getAmount(itemId);
			}
			player.getPacketSender().sendMessage("@red@"
					+ Misc.insertCommasToNumber("" + amount + "") + "x coins.");
			return;
		}

		// Blowpipe
		if (itemId == 12926) {
			player.getPacketSender()
					.sendMessage("Fires Dragon darts while coating them with venom. Charges left: "
							+ player.getBlowpipeScales());
			return;
		}

		ItemDefinition itemDef = ItemDefinition.forId(itemId);
		if (itemDef != null) {
			player.getPacketSender().sendMessage(itemDef.getExamine());
		}
	}

}
