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

public class DragonScimitarCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(1872, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(347, GraphicHeight.HIGH, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.DRAGON_SCIMITAR.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        if (!hit.isAccurate() || !hit.getTarget().isPlayer()) {
            return;
        }
        CombatFactory.disableProtectionPrayers(hit.getTarget().getAsPlayer());
        hit.getAttacker().getAsPlayer().getPacketSender().sendMessage("Your target can no longer use protection prayers.");
    }
}