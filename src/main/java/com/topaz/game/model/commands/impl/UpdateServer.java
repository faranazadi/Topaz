package com.topaz.game.model.commands.impl;

import com.topaz.Server;
import com.topaz.game.World;
import com.topaz.game.content.clan.ClanChatManager;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;
import com.topaz.game.task.Task;
import com.topaz.game.task.TaskManager;

public class UpdateServer implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int time = Integer.parseInt(parts[1]);
        if (time > 0) {
            Server.setUpdating(true);
            for (Player players : World.getPlayers()) {
                if (players == null) {
                    continue;
                }
                players.getPacketSender().sendSystemUpdate(time);
            }
            TaskManager.submit(new Task(time) {
                @Override
                protected void execute() {
                    for (Player player : World.getPlayers()) {
                        if (player != null) {
                            player.requestLogout();
                        }
                    }
                    ClanChatManager.save();
                    Server.getLogger().info("Update task finished!");
                    stop();
                }
            });
        }
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
