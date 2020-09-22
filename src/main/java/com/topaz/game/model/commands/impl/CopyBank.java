package com.topaz.game.model.commands.impl;

import java.util.Optional;

import com.topaz.game.World;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Item;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.container.impl.Bank;
import com.topaz.game.model.rights.PlayerRights;

public class CopyBank implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        String player2 = command.substring(parts[0].length() + 1);
        Optional<Player> plr = World.getPlayerByName(player2);
        if (plr.isPresent()) {
            for (int i = 0; i < Bank.TOTAL_BANK_TABS; i++) {
                if (player.getBank(i) != null) {
                    player.getBank(i).resetItems();
                }
            }
            for (int i = 0; i < Bank.TOTAL_BANK_TABS; i++) {
                if (plr.get().getBank(i) != null) {
                    for (Item item : plr.get().getBank(i).getValidItems()) {
                        player.getBank(i).add(item, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}
