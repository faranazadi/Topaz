package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.util.Misc;

public class TimePlayed implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.forceChat("I've been playing for " + Misc.getFormattedPlayTime(player) + ".");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
