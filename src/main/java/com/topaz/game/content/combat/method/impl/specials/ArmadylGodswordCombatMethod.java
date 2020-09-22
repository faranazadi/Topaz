package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.Priority;

public class ArmadylGodswordCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(7644, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1211, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.ARMADYL_GODSWORD.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }
}