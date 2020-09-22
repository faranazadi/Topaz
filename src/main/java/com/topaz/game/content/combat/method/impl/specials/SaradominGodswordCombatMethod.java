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

public class SaradominGodswordCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(7640, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1209, Priority.HIGH);

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.SARADOMIN_GODSWORD.getDrainAmount());
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        Player player = hit.getAttacker().getAsPlayer();
        int damage = hit.getTotalDamage();
        int damageHeal = (int) (damage * 0.5);
        int damagePrayerHeal = (int) (damage * 0.25);
        if (player.getSkillManager().getCurrentLevel(Skill.HITPOINTS) < player.getSkillManager()
                .getMaxLevel(Skill.HITPOINTS)) {
            int level = player.getSkillManager().getCurrentLevel(Skill.HITPOINTS) + damageHeal > player
                    .getSkillManager().getMaxLevel(Skill.HITPOINTS)
                            ? player.getSkillManager().getMaxLevel(Skill.HITPOINTS)
                            : player.getSkillManager().getCurrentLevel(Skill.HITPOINTS) + damageHeal;
            player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, level);
        }
        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
                .getMaxLevel(Skill.PRAYER)) {
            int level = player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal > player
                    .getSkillManager().getMaxLevel(Skill.PRAYER) ? player.getSkillManager().getMaxLevel(Skill.PRAYER)
                            : player.getSkillManager().getCurrentLevel(Skill.PRAYER) + damagePrayerHeal;
            player.getSkillManager().setCurrentLevel(Skill.PRAYER, level);
        }
    }
}