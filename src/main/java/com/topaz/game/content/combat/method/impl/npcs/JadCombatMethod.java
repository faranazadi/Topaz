package com.topaz.game.content.combat.method.impl.npcs;

import com.topaz.game.content.combat.CombatType;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.CombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.Priority;
import com.topaz.game.model.Projectile;
import com.topaz.util.Misc;

/**
 * Handles Jad's combat.
 *
 * @author Professor Oak
 */
public class JadCombatMethod extends CombatMethod {

	private static final Animation MAGIC_ATTACK_ANIM = new Animation(2656, Priority.MEDIUM);
	private static final Animation RANGED_ATTACK_ANIM = new Animation(2652, Priority.MEDIUM);
	private static final Animation MELEE_ATTACK_ANIM = new Animation(2655, Priority.MEDIUM);
	private static final int MAGIC_ATTACK_PROJECTILE = 448;
	private static final Graphic RANGED_ATTACK_GRAPHIC = new Graphic(451, Priority.MEDIUM);
	private CombatType combatType;

	@Override
	public void start(Mobile character, Mobile target) {
		combatType = Misc.getRandom(1) == 0 ? CombatType.RANGED : CombatType.MAGIC;
        if (character.calculateDistance(target) <= 1 && Misc.getRandom(1) == 0) {
            combatType = CombatType.MELEE;
        }
		switch (combatType) {
        case MELEE:
            character.performAnimation(MELEE_ATTACK_ANIM);
            break;
        case RANGED:
            character.performAnimation(RANGED_ATTACK_ANIM);
            target.delayedGraphic(RANGED_ATTACK_GRAPHIC, 2);
            break;
        case MAGIC:
            character.performAnimation(MAGIC_ATTACK_ANIM);
            new Projectile(character, target, MAGIC_ATTACK_PROJECTILE, 25, 100, 110, 33).sendProjectile();
            break;
        default:
            break;
		}
	}

	@Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        int hitDelay = (combatType == CombatType.MELEE ? 1 : 3);
        return new PendingHit[] { new PendingHit(character, target, this, hitDelay) };
    }

	@Override
	public int attackDistance(Mobile character) {
		return 10;
	}

	@Override
	public CombatType type() {
		return combatType;
	}
}
