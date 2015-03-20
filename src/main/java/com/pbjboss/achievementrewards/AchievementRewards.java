package com.pbjboss.achievementrewards;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

@Mod(modid = "achievementrewards", name = "Achievement Rewards", version ="1.7.10-1.0", acceptableRemoteVersions = "*")
public class AchievementRewards
{
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("Pre-Initializing Achievement Rewards");
        RewardHandler.init(event.getModConfigurationDirectory());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
