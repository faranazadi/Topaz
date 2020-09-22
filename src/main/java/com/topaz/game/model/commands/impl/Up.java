package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Location;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class Up implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Location newLocation = player.getLocation().clone().setZ(player.getLocation().getZ() + 1);
        player.moveTo(newLocation);
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getRights() == PlayerRights.OWNER || player.getRights() == PlayerRights.DEVELOPER);
    }
}
