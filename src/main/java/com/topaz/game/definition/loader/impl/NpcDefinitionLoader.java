package com.topaz.game.definition.loader.impl;

import com.google.gson.Gson;
import com.topaz.game.GameConstants;
import com.topaz.game.definition.NpcDefinition;
import com.topaz.game.definition.loader.DefinitionLoader;

import java.io.FileReader;

public class NpcDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Throwable {
    	NpcDefinition.definitions.clear();
        FileReader reader = new FileReader(file());
        NpcDefinition[] defs = new Gson().fromJson(reader, NpcDefinition[].class);
        for (NpcDefinition def : defs) {
            NpcDefinition.definitions.put(def.getId(), def);
        }
        reader.close();
    }

    @Override
    public String file() {
        return GameConstants.DEFINITIONS_DIRECTORY + "npc_defs.json";
    }

}
