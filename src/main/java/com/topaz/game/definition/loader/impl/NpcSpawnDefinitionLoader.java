package com.topaz.game.definition.loader.impl;

import java.io.FileReader;

import com.google.gson.Gson;
import com.topaz.game.GameConstants;
import com.topaz.game.World;
import com.topaz.game.definition.NpcSpawnDefinition;
import com.topaz.game.definition.loader.DefinitionLoader;
import com.topaz.game.entity.impl.npc.NPC;

public class NpcSpawnDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Throwable {
        FileReader reader = new FileReader(file());
        NpcSpawnDefinition[] defs = new Gson().fromJson(reader, NpcSpawnDefinition[].class);
        for (NpcSpawnDefinition def : defs) {
            NPC npc = NPC.create(def.getId(), def.getPosition());
            npc.getMovementCoordinator().setRadius(def.getRadius());
            npc.setFace(def.getFacing());
            World.getAddNPCQueue().add(npc);
        }
        reader.close();
    }

    @Override
    public String file() {
        return GameConstants.DEFINITIONS_DIRECTORY + "npc_spawns.json";
    }

}
