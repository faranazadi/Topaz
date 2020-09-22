package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.GraphicHeight;
import com.topaz.game.model.Priority;

public class AbyssalWhipCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(1658, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(341, GraphicHeight.HIGH, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.ABYSSAL_WHIP.getDrainAmount());
        character.performAnimation(ANIMATION);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        Mobile target = hit.getTarget();

        if (target.getHitpoints() <= 0) {
            return;
        }

        target.performGraphic(GRAPHIC);
        if (target.isPlayer()) {
            Player p = (Player) target;
            int totalRunEnergy = p.getRunEnergy() - 25;
            if (totalRunEnergy < 0) {
                totalRunEnergy = 0;
            }
            p.setRunEnergy(totalRunEnergy);
            p.getPacketSender().sendRunEnergy();
            if (totalRunEnergy == 0) {
                p.setRunning(false);
                p.getPacketSender().sendRunStatus();
            }
        }
    }
}