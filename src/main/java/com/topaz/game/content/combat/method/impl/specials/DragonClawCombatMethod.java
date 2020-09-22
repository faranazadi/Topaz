package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.Priority;

public class DragonClawCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(7527, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1171, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        PendingHit hit = new PendingHit(character, target, this, true, 4, 0);
		// Modify the hits.. Claws have a unique maxhit formula
        int first = hit.getHits()[0].getDamage();
        int second = first <= 0 ? hit.getHits()[1].getDamage() : (first / 2);
        int third = second <= 0 ? second : (second / 2);
        int fourth = second <= 0 ? second : (second / 2);
        hit.getHits()[0].setDamage(first);
        hit.getHits()[1].setDamage(second);
        hit.getHits()[2].setDamage(third);
        hit.getHits()[3].setDamage(fourth);
        hit.updateTotalDamage();
        return new PendingHit[]{hit};
    }
    
    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.DRAGON_CLAWS.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }
}