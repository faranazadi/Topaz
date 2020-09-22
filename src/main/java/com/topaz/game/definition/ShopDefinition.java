package com.topaz.game.definition;

import com.topaz.game.model.Item;

/**
 * Represents a definition for a shop.
 *
 * @author Professor Oak
 */
public class ShopDefinition {

    private int id;
    private String name = "";
    private Item[] originalStock;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Item[] getOriginalStock() {
        return originalStock;
    }
}
