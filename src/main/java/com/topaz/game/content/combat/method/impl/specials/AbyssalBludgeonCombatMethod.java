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

public class AbyssalBludgeonCombatMethod extends MeleeCombatMethod {

    private static final Animation ANIMATION = new Animation(3299, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1284, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        PendingHit hit = new PendingHit(character, target, this);

        if (character.isPlayer()) {
            Player player = character.getAsPlayer();
            final int missingPrayer = player.getSkillManager().getMaxLevel(Skill.PRAYER) - player.getSkillManager().getCurrentLevel(Skill.PRAYER);
            int extraDamage = (int) (missingPrayer * 0.5);
            hit.getHits()[0].incrementDamage(extraDamage);
            hit.updateTotalDamage();
        }

        return new PendingHit[]{hit};
    }

    @Override
    public void start(Mobile character, Mobile target) {
        CombatSpecial.drain(character, CombatSpecial.ABYSSAL_DAGGER.getDrainAmount());
        character.performAnimation(ANIMATION);
    }
    
    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        hit.getTarget().performGraphic(GRAPHIC);
    }
}