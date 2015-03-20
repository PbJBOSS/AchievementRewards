package com.pbjboss.achievementrewards;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EventHandler
{
    @SubscribeEvent
    public void achievementEvent(AchievementEvent event)
    {
        if (event.entityPlayer.worldObj.isRemote)
            return;

        EntityPlayerMP playerMP = (EntityPlayerMP) event.entityPlayer;
        StatFileWriter fw = playerMP.func_147099_x();

        if (fw.canUnlockAchievement(event.achievement) && !fw.hasAchievementUnlocked(event.achievement))
        {
            RewardHandler.rewardPlayer(((EntityPlayerMP) event.entityPlayer));
        }
    }

}
