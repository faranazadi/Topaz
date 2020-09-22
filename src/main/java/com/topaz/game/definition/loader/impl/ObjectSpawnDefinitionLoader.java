package com.topaz.game.definition.loader.impl;

import com.google.gson.Gson;
import com.topaz.game.GameConstants;
import com.topaz.game.definition.ObjectSpawnDefinition;
import com.topaz.game.definition.loader.DefinitionLoader;
import com.topaz.game.entity.impl.object.GameObject;
import com.topaz.game.entity.impl.object.ObjectManager;

import java.io.FileReader;

public class ObjectSpawnDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Throwable {
        FileReader reader = new FileReader(file());
        ObjectSpawnDefinition[] defs = new Gson().fromJson(reader, ObjectSpawnDefinition[].class);
        for (ObjectSpawnDefinition def : defs) {
            ObjectManager.register(new GameObject(def.getId(), def.getPosition(), def.getType(), def.getFace(), null), true);
        }
        reader.close();
    }

    @Override
    public String file() {
        return GameConstants.DEFINITIONS_DIRECTORY + "object_spawns.json";
    }
}