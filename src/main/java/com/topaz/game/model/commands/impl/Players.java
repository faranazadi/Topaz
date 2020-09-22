package com.topaz.game.model.commands.impl;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.dialogues.DialogueManager;
import com.topaz.game.model.rights.DonatorRights;

public class Players implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
    	player.getPacketSender().sendMessage("There are currently " + World.getPlayers().size() + " players online.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
