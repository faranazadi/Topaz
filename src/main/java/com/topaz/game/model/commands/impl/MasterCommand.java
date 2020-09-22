package com.topaz.game.model.commands.impl;

import com.topaz.game.content.combat.WeaponInterfaces;
import com.topaz.game.content.skill.SkillManager;
import com.topaz.game.entity.impl.player.Player;
import com.topaz.game.model.Flag;
import com.topaz.game.model.Skill;
import com.topaz.game.model.commands.Command;
import com.topaz.game.model.rights.PlayerRights;

public class MasterCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        for (Skill skill : Skill.values()) {
            int level = SkillManager.getMaxAchievingLevel(skill);
            player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                    SkillManager.getExperienceForLevel(level));
        }
        WeaponInterfaces.assign(player);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    @Override
    public boolean canUse(Player player) {
        PlayerRights rights = player.getRights();
        return (rights == PlayerRights.OWNER || rights == PlayerRights.DEVELOPER);
    }

}