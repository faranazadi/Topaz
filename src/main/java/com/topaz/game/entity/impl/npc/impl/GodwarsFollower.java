package com.topaz.game.entity.impl.npc.impl;

import com.topaz.game.entity.impl.npc.NPC;
import com.topaz.game.model.God;
import com.topaz.game.model.Location;

public class GodwarsFollower extends NPC {
	
	private final God god;

	public GodwarsFollower(int id, Location position, God god) {
		super(id, position);
		this.god = god;
	}

	public God getGod() {
		return god;
	}
}
