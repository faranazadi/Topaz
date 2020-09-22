package com.topaz.game.content.combat.method;

import com.topaz.game.content.combat.CombatType;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.entity.impl.Mobile;

/**
 * Represents a combat method.
 */
public abstract class CombatMethod {
    
    public void start(Mobile character, Mobile target) {
    }
    
    public void finished(Mobile character, Mobile target) {
    }
    
    public void handleAfterHitEffects(PendingHit hit) {
    }
    
    public boolean canAttack(Mobile character, Mobile target) {
        return true;
    }
    
    public int attackSpeed(Mobile character) {
        return character.getBaseAttackSpeed();
    }
    
    public int attackDistance(Mobile character) {
        return 1;
    }
    
    public abstract CombatType type();
    public abstract PendingHit[] hits(Mobile character, Mobile target);
}