package com.topaz.game.definition.loader.impl;

import com.google.gson.Gson;
import com.topaz.game.GameConstants;
import com.topaz.game.definition.ItemDefinition;
import com.topaz.game.definition.loader.DefinitionLoader;

import java.io.FileReader;

public class ItemDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Throwable {
    	ItemDefinition.definitions.clear();
        FileReader reader = new FileReader(file());
        ItemDefinition[] defs = new Gson().fromJson(reader, ItemDefinition[].class);
        for (ItemDefinition def : defs) {
            ItemDefinition.definitions.put(def.getId(), def);
        }
        reader.close();
    }

    @Override
    public String file() {
        return GameConstants.DEFINITIONS_DIRECTORY + "items.json";
    }
}
