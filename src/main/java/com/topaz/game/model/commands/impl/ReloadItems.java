package com.topaz.game.model.commands.impl;

import com.topaz.game.definition.loader.impl.ItemDefinitionLoader;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class ReloadItems implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        try {
        	new ItemDefinitionLoader().load();
        	player.getPacketSender().sendMessage("Reloaded item defs");
        } catch(Throwable e) {
        	e.printStackTrace();
        	player.getPacketSender().sendMessage("Error reloading item defs");
        }
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
