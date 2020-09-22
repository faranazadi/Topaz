package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;

public class Claim implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendMessage("To claim purchased items, please talk to the Financial Advisor at home.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
