package com.topaz.game.model.areas.impl;

import java.util.Arrays;
import java.util.Optional;

import com.topaz.game.content.minigames.Barrows;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Boundary;
import com.topaz.game.model.areas.Area;

public class BarrowsArea extends Area {

    public BarrowsArea() {
        super(Arrays.asList(new Boundary(3521, 3582, 9662, 9724), new Boundary(3545, 3583, 3265, 3306)));
    }

    @Override
    public void enter(Mobile character) {
        if (character.isPlayer()) {
            Player player = character.getAsPlayer();
            player.getPacketSender().sendWalkableInterface(Barrows.KILLCOUNTER_INTERFACE_ID);
            Barrows.updateInterface(player);
        }
    }

    @Override
    public void leave(Mobile character, boolean logout) {
        if (character.isPlayer()) {
            character.getAsPlayer().getPacketSender().sendWalkableInterface(-1);
        }
    }

    @Override
    public void process(Mobile character) {
    }

    @Override
    public boolean canTeleport(Player player) {
        return true;
    }

    @Override
    public boolean canAttack(Mobile attacker, Mobile target) {
        if (attacker.isPlayer() && target.isPlayer()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canTrade(Player player, Player target) {
        return true;
    }

    @Override
    public boolean isMulti(Mobile character) {
        return false;
    }

    @Override
    public boolean canEat(Player player, int itemId) {
        return true;
    }

    @Override
    public boolean canDrink(Player player, int itemId) {
        return true;
    }

    @Override
    public boolean dropItemsOnDeath(Player player, Optional<Player> killer) {
        return true;
    }

    @Override
    public boolean handleDeath(Player player, Optional<Player> killer) {
        return false;
    }

    @Override
    public void onPlayerRightClick(Player player, Player rightClicked, int option) {
    }

    @Override
    public void defeated(Player player, Mobile character) {
        if (character.isNpc()) {
            Barrows.brotherDeath(player, character.getAsNpc());
        }
    }
    
    @Override
    public boolean overridesNpcAggressionTolerance(Player player, int npcId) {
        return false;
    }

    @Override
    public boolean handleObjectClick(Player player, int objectId, int type) {
        return Barrows.handleObject(player, objectId);
    }
}
