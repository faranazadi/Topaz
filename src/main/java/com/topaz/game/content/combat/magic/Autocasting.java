package com.topaz.game.content.combat.magic;

import com.topaz.game.content.combat.WeaponInterfaces.WeaponInterface;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Skill;
import com.topaz.game.model.container.impl.Equipment;
import com.topaz.game.model.equipment.BonusManager;

public class Autocasting {
	
	public static final int ANCIENT_STAFF = 4675;

    public static boolean toggleAutocast(final Player player, int actionButtonId) {
        CombatSpell cbSpell = CombatSpells.getCombatSpell(actionButtonId);
        if (cbSpell == null) {
            return false;
        }
        if (cbSpell.levelRequired() > player.getSkillManager().getCurrentLevel(Skill.MAGIC)) {
            player.getPacketSender().sendMessage("You need a Magic level of at least " + cbSpell.levelRequired() + " to cast this spell.");
            setAutocast(player, null);
            return true;
        }

        if (hasStaffEquipped(player)) {
        	if (player.getCombat().getAutocastSpell() != null && player.getCombat().getAutocastSpell() == cbSpell) {
                //Player is already autocasting this spell. Turn it off.
                setAutocast(player, null);
            } else {
                //Set the new autocast spell
                setAutocast(player, cbSpell);
            }
        } else {
        	player.getPacketSender().sendMessage("You must have a staff equipped to be able to autocast!");
        }
        
        
        return true;
    }

    public static void setAutocast(Player player, CombatSpell spell) {
        if (spell == null) {
            player.getPacketSender().sendAutocastId(-1).sendConfig(108, 3);
        } else {
            player.getPacketSender().sendAutocastId(spell.spellId()).sendConfig(108, 1);
        }
        player.getCombat().setAutocastSpell(spell);

        BonusManager.update(player);
    }
    
    public static boolean hasStaffEquipped(Player player) {
    	final int equippedWeapon = player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
    	if (player.getWeapon() == WeaponInterface.STAFF || equippedWeapon == ANCIENT_STAFF) {
	    	return true;
    	}
		return false;
    	
    }
}

