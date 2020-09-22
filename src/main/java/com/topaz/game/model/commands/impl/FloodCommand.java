package com.topaz.game.model.commands.impl;

import com.topaz.Server;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class FloodCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int amt = Integer.parseInt(parts[1]);
        Server.getFlooder().login(amt);
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
