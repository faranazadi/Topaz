package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;

public class Store implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("http://www.deadlypkers.net");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
