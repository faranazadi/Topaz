package com.topaz.game.task.impl;

import com.topaz.game.World;
import com.topaz.game.entity.impl.npc.NPC;
import com.topaz.game.task.Task;

/**
 * A {@link Task} implementation which handles the respawn of an npc.
 *
 * @author Professor Oak
 */
public class NPCRespawnTask extends Task {

    /**
     * The {@link NPC} which is going to respawn.
     */
    private final NPC npc;

    public NPCRespawnTask(NPC npc, int ticks) {
        super(ticks);
        this.npc = npc;
    }

    @Override
    public void execute() {
        // Register the new entity..
        World.getAddNPCQueue().add(npc.clone());

        // Stop the task
        stop();
    }
}
