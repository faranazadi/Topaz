package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatFactory;
import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.GraphicHeight;
import com.topaz.game.model.Priority;
import com.topaz.game.task.impl.CombatPoisonEffect.PoisonType;
import com.topaz.util.Misc;

public class AbyssalTentacleCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(1658, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(181, GraphicHeight.HIGH, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.ABYSSAL_TENTACLE.getDrainAmount());
        character.performAnimation(ANIMATION);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        Mobile target = hit.getTarget();

        if (target.getHitpoints() <= 0) {
            return;
        }
        
        target.performGraphic(GRAPHIC);
        CombatFactory.freeze(target, 10);
        if (Misc.getRandom(100) < 50) {
            CombatFactory.poisonEntity(target, PoisonType.EXTRA);
        }
    }
}