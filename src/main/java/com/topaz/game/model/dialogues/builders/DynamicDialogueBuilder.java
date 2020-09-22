package com.topaz.game.model.dialogues.builders;

import com.topaz.game.entity.impl.player.Player;

public abstract class DynamicDialogueBuilder extends DialogueBuilder {
    public abstract void build(Player player);
}
