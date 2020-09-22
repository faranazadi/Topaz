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
import com.topaz.util.Misc;

public class BandosGodswordCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(7642, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1212, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.BANDOS_GODSWORD.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        if (hit.isAccurate() && hit.getTarget().isPlayer()) {
            int skillDrain = 1;
            int damageDrain = (int) (hit.getTotalDamage() * 0.1);
            if (damageDrain < 0)
                return;
            Player player = hit.getAttacker().getAsPlayer();
            Player target = hit.getTarget().getAsPlayer();
            final Skill skill = Skill.values()[skillDrain];
            target.getSkillManager().setCurrentLevel(skill, player.getSkillManager().getCurrentLevel(skill) - damageDrain);
            if (target.getSkillManager().getCurrentLevel(skill) < 1)
                target.getSkillManager().setCurrentLevel(skill, 1);
            player.getPacketSender().sendMessage("You've drained " + target.getUsername() + "'s " + Misc.formatText(Skill.values()[skillDrain].toString().toLowerCase()) + " level by " + damageDrain + ".");
            target.getPacketSender().sendMessage("Your " + skill.getName() + " level has been drained.");
        }
    }
}