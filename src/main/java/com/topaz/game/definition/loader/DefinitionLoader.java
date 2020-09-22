package com.topaz.game.definition.loader;

import java.util.logging.Level;

import com.topaz.Server;

/**
 * An abstract class which handles the loading
 * of some sort of definition-related file.
 *
 * @author Professor Oak
 */
public abstract class DefinitionLoader implements Runnable {

    public abstract void load() throws Throwable;
    public abstract String file();

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            load();
            long elapsed = System.currentTimeMillis() - start;
            Server.getLogger().log(Level.INFO, "Loaded definitions for " + file() + " in " + elapsed + " ms.");
        } catch (Throwable e) {
            e.printStackTrace();
            Server.getLogger().log(Level.SEVERE, "Error loading definitions for: " + file(), e);
        }
    }
}
