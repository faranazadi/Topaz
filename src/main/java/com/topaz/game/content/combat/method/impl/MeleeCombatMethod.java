package com.topaz.game.content.combat.method.impl;

import com.topaz.game.content.combat.CombatType;
import com.topaz.game.content.combat.WeaponInterfaces.WeaponInterface;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.CombatMethod;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.model.Animation;

public class MeleeCombatMethod extends CombatMethod {

    @Override
    public void start(Mobile character, Mobile target) {
        int animation = character.getAttackAnim();
        if (animation != -1) {
            character.performAnimation(new Animation(animation));
        }
    }
    
	@Override
	public CombatType type() {
		return CombatType.MELEE;
	}

	@Override
	public PendingHit[] hits(Mobile character, Mobile target) {
        return new PendingHit[] { new PendingHit(character, target, this) };
	}

	@Override
	public int attackDistance(Mobile character) {
        if (character.isPlayer() && character.getAsPlayer().getWeapon() == WeaponInterface.HALBERD) {
            return 2;
        }
		return 1;
	}
}
