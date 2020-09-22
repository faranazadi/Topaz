package com.topaz.game.model.commands.impl;

import com.topaz.game.collision.RegionManager;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.dialogues.builders.impl.NieveDialogue;
import com.topaz.game.model.rights.PlayerRights;

public class DebugCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        System.out.println(RegionManager.wallsExist(player.getLocation().clone(), player.getPrivateArea()));
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getRights() == PlayerRights.DEVELOPER);
    }

}
