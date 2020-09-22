package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.MeleeCombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.Priority;
import com.topaz.game.model.Skill;

public class DragonWarhammerCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(1378, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1292, Priority.HIGH);
    
    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.DRAGON_WARHAMMER.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        if (hit.isAccurate() && hit.getTarget().isPlayer()) {
            int damageDrain = (int) (hit.getTotalDamage() * 0.3);
            if (damageDrain < 0)
                return;
            Player player = hit.getAttacker().getAsPlayer();
            Player target = hit.getTarget().getAsPlayer();
            target.getSkillManager().decreaseCurrentLevel(Skill.DEFENCE, damageDrain, 1);
            player.getPacketSender().sendMessage("You've drained " + target.getUsername() + "'s Defence level by " + damageDrain + ".");
            target.getPacketSender().sendMessage("Your Defence level has been drained.");
        }
    }
}