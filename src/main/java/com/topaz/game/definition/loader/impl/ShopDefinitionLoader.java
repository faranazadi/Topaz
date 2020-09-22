package com.topaz.game.definition.loader.impl;

import com.google.gson.Gson;
import com.topaz.game.GameConstants;
import com.topaz.game.definition.ShopDefinition;
import com.topaz.game.definition.loader.DefinitionLoader;
import com.topaz.game.model.container.shop.Shop;
import com.topaz.game.model.container.shop.ShopManager;

import java.io.FileReader;

public class ShopDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Throwable {
    	ShopManager.shops.clear();
        FileReader reader = new FileReader(file());
        ShopDefinition[] defs = new Gson().fromJson(reader, ShopDefinition[].class);
        for (ShopDefinition def : defs) {
            ShopManager.shops.put(def.getId(), new Shop(def.getId(), def.getName(), def.getOriginalStock()));
        }
        reader.close();
    }

    @Override
    public String file() {
        return GameConstants.DEFINITIONS_DIRECTORY + "shops.json";
    }
}
