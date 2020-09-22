package com.topaz.net.packet.impl;

import com.topaz.game.GameConstants;
import com.topaz.game.definition.ItemDefinition;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.areas.impl.WildernessArea;
import com.topaz.game.model.container.impl.Bank;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

/**
 * This packet listener reads a item spawn request from the spawn tab.
 *
 * @author Professor Oak
 */

public class SpawnItemPacketListener implements PacketExecutor {

    public static void spawn(Player player, int item, int amount, boolean toBank) {

        if (amount < 0) {
            return;
        } else if (amount > Integer.MAX_VALUE) {
            amount = Integer.MAX_VALUE;
        }

        // Check if player busy..
        if (player.busy() || player.getArea() instanceof WildernessArea) {
            player.getPacketSender().sendMessage("You cannot do that right now.");
            return;
        }

        boolean spawnable = false;
        for (int i : GameConstants.ALLOWED_SPAWNS) {
            if (item == i) {
                spawnable = true;
                break;
            }
        }

        ItemDefinition def = ItemDefinition.forId(item);
        if (def == null || !spawnable) {
            player.getPacketSender().sendMessage("This item is currently unavailable.");
            return;
        }

        // Spawn item.
        if (toBank) {
            player.getBank(Bank.getTabForItem(player, item)).add(item, amount);
        } else {

            if (amount > player.getInventory().getFreeSlots()) {
            	amount = player.getInventory().getFreeSlots();
            }
            
            if (amount <= 0) {
            	player.getInventory().full();
            	return;
            }
               
            player.getInventory().add(item, amount);
        }

        player.getPacketSender()
                .sendMessage("Spawned " + def.getName() + " to " + (toBank ? ("bank") : ("inventory")) + ".");
    }

    @Override
    public void execute(com.topaz.game.entity.impl.player.Player player, Packet packet) {
        final int item = packet.readInt();
        final boolean spawnX = packet.readByte() == 1;
        final boolean toBank = packet.readByte() == 1;

        ItemDefinition def = ItemDefinition.forId(item);
        if (def == null) {
            player.getPacketSender().sendMessage("This item is currently unavailable.");
            return;
        }

        if (spawnX) {
            player.setEnteredAmountAction((amount) -> {
                spawn(player, item, amount, toBank);
            });
            player.getPacketSender().sendEnterAmountPrompt("How many " + def.getName() + " would you like to spawn?");
        } else {
            spawn(player, item, 1, toBank);
        }
    }
}
