package com.topaz.net.packet.impl;

import com.topaz.game.content.clan.ClanChatManager;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.ChatMessage;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketConstants;
import com.topaz.net.packet.PacketExecutor;
import com.topaz.util.Misc;
import com.topaz.util.PlayerPunishment;

/**
 * This packet listener manages the spoken text by a player. Either sent to the
 * regular chatbox or to a clanchat channel.
 *
 * @author Gabriel Hannason
 */

public class ChatPacketListener implements PacketExecutor {

    private static boolean allowChat(Player player, String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You are muted and cannot chat.");
            return false;
        }
        if (Misc.blockedWord(text)) {
            player.getPacketSender().sendMessage("Your message did not make it past the filter.");
            return false;
        }
        return true;
    }

    @Override
    public void execute(Player player, Packet packet) {
        switch (packet.getOpcode()) {
        case PacketConstants.CLAN_CHAT_OPCODE:
            String clanMessage = packet.readString();
            if (!allowChat(player, clanMessage)) {
                return;
            }
            ClanChatManager.sendMessage(player, clanMessage);
            break;
        case PacketConstants.REGULAR_CHAT_OPCODE:
            int size = packet.getSize() - 2;
            int color = packet.readByteS();
            int effect = packet.readByteS();
            byte[] text = packet.readReversedBytesA(size);
            String chatMessage = Misc.ucFirst(Misc.textUnpack(text, size).toLowerCase());

            if (!allowChat(player, chatMessage)) {
                return;
            }
            if (player.getChatMessageQueue().size() >= 5) {
                return;
            }
            player.getChatMessageQueue().add(new ChatMessage(color, effect, text));
            break;
        }
    }
}
