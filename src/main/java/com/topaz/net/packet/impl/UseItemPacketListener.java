package com.topaz.net.packet.impl;

import java.util.Arrays;
import java.util.Optional;

import com.topaz.Server;
import com.topaz.game.World;
import com.topaz.game.content.combat.CombatFactory;
import com.topaz.game.content.skill.skillable.impl.Cooking;
import com.topaz.game.content.skill.skillable.impl.Crafting;
import com.topaz.game.content.skill.skillable.impl.Firemaking;
import com.topaz.game.content.skill.skillable.impl.Fletching;
import com.topaz.game.content.skill.skillable.impl.Herblore;
import com.topaz.game.content.skill.skillable.impl.Cooking.Cookable;
import com.topaz.game.content.skill.skillable.impl.Firemaking.LightableLog;
import com.topaz.game.content.skill.skillable.impl.Prayer.AltarOffering;
import com.topaz.game.content.skill.skillable.impl.Prayer.BuriableBone;
import com.topaz.game.entity.impl.grounditem.ItemOnGround;
import com.topaz.game.entity.impl.grounditem.ItemOnGroundManager;
import com.topaz.game.entity.impl.npc.NPC;
import com.topaz.game.entity.impl.object.GameObject;
import com.topaz.game.entity.impl.object.MapObjects;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Item;
import com.topaz.game.model.Location;
import com.topaz.game.model.menu.CreationMenu;
import com.topaz.game.model.movement.WalkToAction;
import com.topaz.net.packet.Packet;
import com.topaz.net.packet.PacketConstants;
import com.topaz.net.packet.PacketExecutor;
import com.topaz.util.ItemIdentifiers;
import com.topaz.util.ObjectIdentifiers;


public class UseItemPacketListener extends ItemIdentifiers implements PacketExecutor {

    private static void itemOnItem(Player player, Packet packet) {
        int usedWithSlot = packet.readUnsignedShort();
        int itemUsedSlot = packet.readUnsignedShortA();
        if (usedWithSlot < 0 || itemUsedSlot < 0
                || itemUsedSlot >= player.getInventory().capacity()
                || usedWithSlot >= player.getInventory().capacity())
            return;
        Item used = player.getInventory().getItems()[itemUsedSlot];
        Item usedWith = player.getInventory().getItems()[usedWithSlot];

        player.getPacketSender().sendInterfaceRemoval();
        player.getSkillManager().stopSkillable();

        //Herblore
        if (Herblore.makeUnfinishedPotion(player, used.getId(), usedWith.getId())
                || Herblore.finishPotion(player, used.getId(), usedWith.getId())
                || Herblore.concatenate(player, used, usedWith)) {
            return;
        }

        //Fletching
        if (Fletching.fletchLog(player, used.getId(), usedWith.getId())
                || Fletching.stringBow(player, used.getId(), usedWith.getId())
                || Fletching.fletchAmmo(player, used.getId(), usedWith.getId())
                || Fletching.fletchCrossbow(player, used.getId(), usedWith.getId())) {
            return;
        }

        //Crafting
        if (Crafting.craftGem(player, used.getId(), usedWith.getId())) {
            return;
        }

        //Firemaking
        if (Firemaking.init(player, used.getId(), usedWith.getId())) {
            return;
        }

        //Granite clamp on Granite maul
        if ((used.getId() == GRANITE_CLAMP || usedWith.getId() == GRANITE_CLAMP)
                && (used.getId() == GRANITE_MAUL || usedWith.getId() == GRANITE_MAUL)) {
            if (player.busy() || CombatFactory.inCombat(player)) {
                player.getPacketSender().sendMessage("You cannot do that right now.");
                return;
            }
            if (player.getInventory().contains(GRANITE_MAUL)) {
                player.getInventory().delete(GRANITE_MAUL, 1).delete(GRANITE_CLAMP, 1).add(GRANITE_MAUL_3, 1);
                player.getPacketSender().sendMessage("You attach your Granite clamp onto the maul..");
            }
            return;
        }

        //Blowpipe reload
        else if (used.getId() == TOXIC_BLOWPIPE || usedWith.getId() == TOXIC_BLOWPIPE) {
            int reload = used.getId() == TOXIC_BLOWPIPE ? usedWith.getId() : used.getId();
            if (reload == ZULRAHS_SCALES) {
                final int amount = player.getInventory().getAmount(12934);
                player.incrementBlowpipeScales(amount);
                player.getInventory().delete(ZULRAHS_SCALES, amount);
                player.getPacketSender().sendMessage("You now have " + player.getBlowpipeScales() + " Zulrah scales in your blowpipe.");
            } else {
                player.getPacketSender().sendMessage("You cannot load the blowpipe with that!");
            }
        }
    }

    private static void itemOnNpc(final Player player, Packet packet) {
        final int id = packet.readShortA();
        final int index = packet.readShortA();
        final int slot = packet.readLEShort();
        if (index < 0 || index > World.getNpcs().capacity()) {
            return;
        }
        if (slot < 0 || slot > player.getInventory().getItems().length) {
            return;
        }
        NPC npc = World.getNpcs().get(index);
        if (npc == null) {
            return;
        }
        if (player.getInventory().getItems()[slot].getId() != id) {
            return;
        }
        switch (id) {

        }
    }

    @SuppressWarnings("unused")
    private static void itemOnObject(Player player, Packet packet) {
        int interfaceType = packet.readShort();
        final int objectId = packet.readShort();
        final int objectY = packet.readLEShortA();
        final int itemSlot = packet.readLEShort();
        final int objectX = packet.readLEShortA();
        final int itemId = packet.readShort();

        if (itemSlot < 0 || itemSlot >= player.getInventory().capacity())
            return;
        final Item item = player.getInventory().getItems()[itemSlot];
        if (item == null || item.getId() != itemId)
            return;
        final Location position = new Location(objectX, objectY, player.getLocation().getZ());
        final GameObject object = MapObjects.get(player, objectId, position);

        // Make sure the object actually exists in the region...
        if (object == null) {
            return;
        }

        //Update facing..
        player.setPositionToFace(position);

        //Handle object..
        switch (object.getId()) {
            case ObjectIdentifiers.STOVE_4: //Edgeville Stove
            case ObjectIdentifiers.FIRE_5: //Player-made Fire
            case ObjectIdentifiers.FIRE_23: //Barb village fire
                //Handle cooking on objects..
                Optional<Cookable> cookable = Cookable.getForItem(item.getId());
                if (cookable.isPresent()) {                    
                    player.getPacketSender().sendCreationMenu(new CreationMenu("How many would you like to cook?", Arrays.asList(cookable.get().getCookedItem()), (productId, amount) -> {
                        player.getSkillManager().startSkillable(new Cooking(object, cookable.get(), amount));
                    }));
                    return;
                }
                //Handle bonfires..
                if (object.getId() == ObjectIdentifiers.FIRE_5) {
                    Optional<LightableLog> log = LightableLog.getForItem(item.getId());
                    if (log.isPresent()) {                        
                        player.getPacketSender().sendCreationMenu(new CreationMenu("How many would you like to burn?", Arrays.asList(log.get().getLogId()), (productId, amount) -> {
                            player.getSkillManager().startSkillable(new Firemaking(log.get(), object, amount));
                        }));
                        return;
                    }
                }
                break;
            case 409: //Bone on Altar
                Optional<BuriableBone> b = BuriableBone.forId(item.getId());
                if (b.isPresent()) {                    
                    player.getPacketSender().sendCreationMenu(new CreationMenu("How many would you like to offer?", Arrays.asList(itemId), (productId, amount) -> {
                        player.getSkillManager().startSkillable(new AltarOffering(b.get(), object, amount));
                    }));
                }
                break;
        }
    }

    @SuppressWarnings("unused")
    private static void itemOnPlayer(Player player, Packet packet) {
        int interfaceId = packet.readUnsignedShortA();
        int targetIndex = packet.readUnsignedShort();
        int itemId = packet.readUnsignedShort();
        int slot = packet.readLEShort();
        if (slot < 0 || slot >= player.getInventory().capacity() || targetIndex >= World.getPlayers().capacity())
            return;
        Player target = World.getPlayers().get(targetIndex);
        if (target == null) {
            return;
        }
    }

    @SuppressWarnings("unused")
    private static void itemOnGroundItem(Player player, Packet packet) {
        int interfaceType = packet.readLEShort();
        int usedItemId = packet.readShortA();
        int groundItemId = packet.readShort();
        int y = packet.readShortA();
        int unknown = packet.readLEShortA();
        int x = packet.readShort();

        //Verify item..
        if (!player.getInventory().contains(usedItemId)) {
            return;
        }

        //Verify ground item..
        Optional<ItemOnGround> groundItem = ItemOnGroundManager.getGroundItem(Optional.of(player.getUsername()), groundItemId, new Location(x, y));
        if (!groundItem.isPresent()) {
            return;
        }

        player.setWalkToTask(new WalkToAction(player) {
            @Override
            public void execute() {
                //Face...
                player.setPositionToFace(groundItem.get().getPosition());

                //Handle used item..
                switch (usedItemId) {
                    case TINDERBOX: //Lighting a fire..
                        Optional<LightableLog> log = LightableLog.getForItem(groundItemId);
                        if (log.isPresent()) {
                            player.getSkillManager().startSkillable(new Firemaking(log.get(), groundItem.get()));
                            return;
                        }
                        break;
                }
            }
            
            @Override
            public boolean inDistance() {
                return player.getLocation().getDistance(groundItem.get().getPosition()) <= 1;
            }
        });
    }


    @Override
    public void execute(Player player, Packet packet) {
        if (player.getHitpoints() <= 0)
            return;
        switch (packet.getOpcode()) {
            case PacketConstants.ITEM_ON_ITEM:
                itemOnItem(player, packet);
                break;
            case PacketConstants.ITEM_ON_OBJECT:
                itemOnObject(player, packet);
                break;
            case PacketConstants.ITEM_ON_GROUND_ITEM:
                itemOnGroundItem(player, packet);
                break;
            case PacketConstants.ITEM_ON_NPC:
                itemOnNpc(player, packet);
                break;
            case PacketConstants.ITEM_ON_PLAYER:
                itemOnPlayer(player, packet);
                break;
        }
    }
}