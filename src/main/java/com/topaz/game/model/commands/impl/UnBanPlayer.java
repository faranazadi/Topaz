package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.entity.impl.player.PlayerSaving;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;
import com.topaz.util.PlayerPunishment;

public class UnBanPlayer implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        String player2 = command.substring(parts[0].length() + 1);

        if (!PlayerSaving.playerExists(player2)) {
            player.getPacketSender().sendMessage("Player " + player2 + " is not online.");
            return;
        }

        if (!PlayerPunishment.banned(player2)) {
            player.getPacketSender().sendMessage("Player " + player2 + " is not banned!");
            return;
        }

    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}