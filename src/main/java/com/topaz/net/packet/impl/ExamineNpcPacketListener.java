package com.topaz.net.packet.impl;

import com.topaz.game.definition.NpcDefinition;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketExecutor;

public class ExamineNpcPacketListener implements PacketExecutor {

    @Override
    public void execute(Player player, Packet packet) {
        int npcId = packet.readShort();
        
        if (npcId <= 0) {
            return;
        }

        NpcDefinition npcDef = NpcDefinition.forId(npcId);
        if (npcDef != null) {
            player.getPacketSender().sendMessage(npcDef.getExamine());
        }
    }

}
