package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatFactory;
import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.RangedCombatMethod;
import com.topaz.game.content.combat.ranged.RangedData.RangedWeapon;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Priority;
import com.topaz.game.model.Projectile;

public class BallistaCombatMethod extends RangedCombatMethod {

    private static final Animation ANIMATION = new Animation(7222, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        return new PendingHit[]{new PendingHit(character, target, this, 2)};
    }

    @Override
    public boolean canAttack(Mobile character, Mobile target) {
        if (!character.isPlayer()) {
            return false;
        }
        Player player = character.getAsPlayer();
        if (player.getCombat().getRangedWeapon() != RangedWeapon.BALLISTA) {
            return false;
        }
        if (!CombatFactory.checkAmmo(player, 1)) {
            return false;
        }
        return true;
    }

    @Override
    public void start(Mobile character, Mobile target) {
        final Player player = character.getAsPlayer();
        CombatSpecial.drain(player, CombatSpecial.BALLISTA.getDrainAmount());
        character.performAnimation(ANIMATION);
        new Projectile(player, target, 1301, 70, 30, 43, 31).sendProjectile();
        CombatFactory.decrementAmmo(player, target.getLocation(), 1);
    }
}