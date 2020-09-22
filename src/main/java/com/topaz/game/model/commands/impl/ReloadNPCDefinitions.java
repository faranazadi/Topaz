package com.topaz.game.model.commands.impl;

import com.topaz.game.definition.loader.impl.NpcDefinitionLoader;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class ReloadNPCDefinitions implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        try {
			new NpcDefinitionLoader().load();
			player.getPacketSender().sendConsoleMessage("Reloaded npc defs.");
		} catch (Throwable e) {
			e.printStackTrace();
			player.getPacketSender().sendMessage("Error reloading npc defs.");
		}
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
