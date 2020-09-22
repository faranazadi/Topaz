package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.Priority;

public class AbyssalDaggerCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(3300, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1283, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        PendingHit hit1 = new PendingHit(character, target, this);
        PendingHit hit2 = new PendingHit(character, target, this, target.isNpc() ? 1 : 0);

        if (!hit1.isAccurate() || hit1.getTotalDamage() <= 0) {
            hit2.getHits()[0].setDamage(0);
            hit2.updateTotalDamage();
        }

        return new PendingHit[] { hit1, hit2 };
    }
    
    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.ABYSSAL_DAGGER.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }
}