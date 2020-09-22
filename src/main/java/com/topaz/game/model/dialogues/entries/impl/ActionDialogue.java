package com.topaz.game.model.dialogues.entries.impl;

import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Action;
import com.topaz.game.model.dialogues.entries.Dialogue;

public class ActionDialogue extends Dialogue {

    private final Action action;
    
    public ActionDialogue(int index, Action action) {
        super(index);
        this.action = action;
    }

    @Override
    public void send(Player player) {
        action.execute();
    }
}
