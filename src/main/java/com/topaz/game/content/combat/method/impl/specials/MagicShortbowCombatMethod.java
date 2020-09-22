package com.topaz.game.content.combat.method.impl.specials;

import com.topaz.game.content.combat.CombatFactory;
import com.topaz.game.content.combat.CombatSpecial;
import com.topaz.game.content.combat.hit.PendingHit;
import com.topaz.game.content.combat.method.impl.RangedCombatMethod;
import com.topaz.game.content.combat.ranged.RangedData.RangedWeapon;
import com.topaz.game.entity.impl.Mobile;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Animation;
import com.topaz.game.model.Graphic;
import com.topaz.game.model.GraphicHeight;
import com.topaz.game.model.Priority;
import com.topaz.game.model.Projectile;

public class MagicShortbowCombatMethod extends RangedCombatMethod {

    private static final Animation ANIMATION = new Animation(1074, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(250, GraphicHeight.HIGH, Priority.HIGH);

    @Override
    public PendingHit[] hits(Mobile character, Mobile target) {
        return new PendingHit[]{new PendingHit(character, target, this, true, 3), new PendingHit(character, target, this, true, 2)};
    }

    @Override
    public boolean canAttack(Mobile character, Mobile target) {
        Player player = character.getAsPlayer();
        if (player.getCombat().getRangedWeapon() != RangedWeapon.MAGIC_SHORTBOW) {
            return false;
        }        
        if (!CombatFactory.checkAmmo(player, 2)) {
            return false;
        }
        return true;
    }

    @Override
    public void start(Mobile character, Mobile target) {
        final Player player = character.getAsPlayer();
        CombatSpecial.drain(player, CombatSpecial.MAGIC_SHORTBOW.getDrainAmount());
        player.performAnimation(ANIMATION);
        player.performGraphic(GRAPHIC);
        new Projectile(player, target, 249, 40, 70, 43, 31).sendProjectile();
        new Projectile(character, target, 249, 33, 74, 48, 31).sendProjectile();
        CombatFactory.decrementAmmo(player, target.getLocation(), 2);
    }

    @Override
    public int attackSpeed(Mobile character) {
        return super.attackSpeed(character) + 1;
    }
}