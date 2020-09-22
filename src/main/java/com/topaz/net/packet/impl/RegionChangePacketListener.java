package com.topaz.net.packet.impl;

import com.topaz.game.collision.RegionManager;
import com.topaz.game.content.minigames.Barrows;
import com.topaz.game.entity.impl.grounditem.ItemOnGroundManager;
import com.topaz.game.entity.impl.npc.NpcAggression;
import com.topaz.game.entity.impl.object.ObjectManager;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class RegionChangePacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        if (player.isAllowRegionChangePacket()) {
            RegionManager.loadMapFiles(player.getLocation().getX(), player.getLocation().getY());
            player.getPacketSender().deleteRegionalSpawns();
            ItemOnGroundManager.onRegionChange(player);
            ObjectManager.onRegionChange(player);
            Barrows.brotherDespawn(player);
            player.getAggressionTolerance().start(NpcAggression.NPC_TOLERANCE_SECONDS); // Every 4 minutes, reset
                                                                                        // aggression for npcs in the
                                                                                        // region.
            player.setAllowRegionChangePacket(false);
        }
    }
}
