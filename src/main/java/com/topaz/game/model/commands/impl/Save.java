package com.topaz.game.model.commands.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.entity.impl.player.PlayerSaving;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class Save implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        PlayerSaving.save(player);
        player.getPacketSender().sendMessage("Saved player.");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getRights() == PlayerRights.DEVELOPER || player.getRights() == PlayerRights.OWNER);
    }
}
