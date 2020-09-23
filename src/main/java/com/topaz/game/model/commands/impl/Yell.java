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
			player.getPacketSender().sendMessage("You must wait another " + player.getYellDelay().secondsRemaining() + " seconds use the yell channel.");
			return;
		}
		final String yellMessage = command.substring(4, command.length());
		if (Misc.blockedWord(yellMessage)) {
			player.getPacketSender().sendMessage("You tried to yell a word deemed unacceptable. Please choose your language carefully.");
			return;
		}

		int spriteId = player.getRights().getSpriteId();
		String sprite = (spriteId == -1 ? "" : "<img=" + spriteId + ">");
		//String yell = ("" + getYellPrefix(player) + sprite + " " + player.getUsername() + ":" + yellMessage);
		
		// Crown draws on the line below the message for some reason, need to fix
		// TODO: fix crowns in yell
		String yell = ("[@red@Global Chat@bla@]" + getYellPrefix(player) + " " + player.getUsername() + ":" + yellMessage);
		World.getPlayers().forEach(e -> e.getPacketSender().sendSpecialMessage(player.getUsername(), 21, yell));
		
		int yellDelay = getYellDelay(player);
		if (yellDelay > 0) {
			player.getYellDelay().start(yellDelay);
		}
	}

	@Override
	public boolean canUse(Player player) {
		if ((player.isStaff() || player.isDonator()) && !GameConstants.CAN_PLAYERS_YELL) {
			return true;
		}
		/*if (GameConstants.CAN_PLAYERS_YELL && GameConstants.IS_YELL_RESTRICTED && (Misc. >= GameConstants.YELL_THRESHOLD)) {
			return true;
		}*/
		if (GameConstants.CAN_PLAYERS_YELL)
		{
			return true;
		}
		player.getPacketSender().sendMessage("Sorry, you do not have the privilege to use the yell channel. The yell channel is for staff, donors and players who have reached " + GameConstants.YELL_THRESHOLD + " hours of gameplay.");
		return false;
	}
}
