package com.topaz.game.model.commands.impl;

import com.topaz.game.GameConstants;
import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.util.Misc;
import com.topaz.util.PlayerPunishment;

public class Yell implements Command {

	public static String getYellPrefix(Player player) {
		if (!player.getRights().getYellTag().isEmpty()) {
			return player.getRights().getYellTag();
		}
		return player.getDonatorRights().getYellTag();
	}

	private static int getYellDelay(Player player) {
		if (player.isStaff()) {
			return 0;
		}
		return player.getDonatorRights().getYellDelay();
	}

	@Override
	public void execute(Player player, String command, String[] parts) {
		if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
			player.getPacketSender().sendMessage("You are muted and cannot yell.");
			return;
		}
		if (!player.getYellDelay().finished()) {
			player.getPacketSender().sendMessage(
					"You must wait another " + player.getYellDelay().secondsRemaining() + " seconds to do that.");
			return;
		}
		final String yellMessage = command.substring(4, command.length());
		if (Misc.blockedWord(yellMessage)) {
		//	DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
			return;
		}

		int spriteId = player.getRights().getSpriteId();
		String sprite = (spriteId == -1 ? "" : "<img=" + spriteId + ">");
		//String yell = ("" + getYellPrefix(player) + sprite + " " + player.getUsername() + ":" + yellMessage);
		
		// Crown draws on the line below the message for some reason, need to fix
		// TODO: fix crowns in yell
		String yell = ("" + getYellPrefix(player) + " " + player.getUsername() + ":" + yellMessage);
		World.getPlayers().forEach(e -> e.getPacketSender().sendSpecialMessage(player.getUsername(), 21, yell));
		
		int yellDelay = getYellDelay(player);
		if (yellDelay > 0) {
			player.getYellDelay().start(yellDelay);
		}
	}

	@Override
	public boolean canUse(Player player) {
		if (player.isStaff() || player.isDonator() || GameConstants.CAN_PLAYERS_YELL == true) {
			return true;
		}
		player.getPacketSender().sendMessage("Sorry, you do not have the privelige to use the yell channel yet. Please donate to use the yell channel or");
		return false;
	}
}
