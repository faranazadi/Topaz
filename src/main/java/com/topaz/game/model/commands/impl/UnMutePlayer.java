package com.topaz.game.model.commands.impl;

import java.util.Optional;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.entity.impl.player.PlayerSaving;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;
import com.topaz.util.PlayerPunishment;

public class UnMutePlayer implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        String player2 = command.substring(parts[0].length() + 1);
        Optional<Player> plr = World.getPlayerByName(player2);

        if (!PlayerSaving.playerExists(player2) && !plr.isPresent()) {
            player.getPacketSender().sendMessage("Player " + player2 + " does not exist.");
            return;
        }

        if (!PlayerPunishment.muted(player2)) {
            player.getPacketSender().sendMessage("Player " + player2 + " does not have an active mute.");
            return;
        }

    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
