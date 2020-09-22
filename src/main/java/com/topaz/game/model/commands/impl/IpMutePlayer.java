package com.topaz.game.model.commands.impl;

import java.util.Optional;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class IpMutePlayer implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Optional<Player> player2 = World.getPlayerByName(command.substring(parts[0].length() + 1));

        if (!player2.isPresent()) {
            player.getPacketSender().sendMessage("Player " + player2 + " is not online.");
            return;
        }

    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
