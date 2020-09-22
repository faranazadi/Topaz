package com.topaz.game.entity.impl.npc.impl;

import com.topaz.game.entity.impl.npc.NPC;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Location;
import com.topaz.game.model.areas.impl.FightCavesArea;

public class Jad extends NPC {

    public Jad(Player player, FightCavesArea area, int id, Location position) {
        super(id, position);
        setOwner(player);
        area.add(this);
    }
    
    @Override
    public int aggressionDistance() {
        return 64;
    }
}
