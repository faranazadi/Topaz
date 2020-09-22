package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.GraphicHeight;
import com.topaz.game.model.Priority;

public class DragonDaggerCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(1062, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(252, GraphicHeight.HIGH, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        return new PendingHit[] { new PendingHit(character, target, this),
                new PendingHit(character, target, this, target.isNpc() ? 1 : 0) };
    }

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.DRAGON_DAGGER.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }
}