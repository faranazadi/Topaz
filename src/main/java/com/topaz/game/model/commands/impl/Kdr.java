package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;

public class Kdr implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.forceChat("My KDR is: " + player.getKillDeathRatio() + "!");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
