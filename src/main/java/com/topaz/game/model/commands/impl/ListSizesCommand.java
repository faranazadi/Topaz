package com.topaz.game.model.commands.impl;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class ListSizesCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendMessage("Players: " + World.getPlayers().size() + ", NPCs: " + World.getNpcs().size() + ", Objects: " + World.getObjects().size() + ", GroundItems: " + World.getItems().size() + ".");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getRights() == PlayerRights.DEVELOPER || player.getRights() == PlayerRights.OWNER);
    }
}
