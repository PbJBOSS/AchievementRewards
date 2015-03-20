package com.pbjboss.achievementrewards;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RewardHandler
{
    public static ArrayList<Item> validItems;
    public static File file;

    public static void rewardPlayer(EntityPlayerMP playerMP)
    {
        ItemStack reward = new ItemStack(validItems.get((int) (Math.random() * validItems.size())));
        reward.stackSize = (int) (Math.random() * reward.getItem().getItemStackLimit() + 1);

        AchievementRewards.logger.info(String.format("Giving Player %s: %s stacksize: %s", playerMP.getDisplayName(), reward.getDisplayName(), reward.stackSize));

        addStackToInventory(reward, playerMP);
    }

    public static void addStackToInventory(ItemStack itemStack, EntityPlayerMP playerMP)
    {
        if (!playerMP.inventory.addItemStackToInventory(itemStack))
        {
            playerMP.worldObj.spawnEntityInWorld(new EntityItem(playerMP.worldObj, playerMP.posX, playerMP.posY, playerMP.posZ, itemStack));
        }
    }

    public static void loadValidItems()
    {
        boolean isEmpty = true;

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = br.readLine()) != null && isEmpty)
            {
                isEmpty = false;
            }

            if (isEmpty)
            {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                Iterator items = Item.itemRegistry.iterator();

                while (items.hasNext())
                {
                    Item item = (Item) items.next();
                    bw.write(item.getUnlocalizedName());
                    if (items.hasNext())
                        bw.newLine();
                }
                bw.close();
            }

            ArrayList<String> list = new ArrayList<String>();

            while ((line = br.readLine()) != null)
            {
                list.add(line);
            }

            Iterator items = Item.itemRegistry.iterator();

            while (items.hasNext())
            {
                Item item = (Item) items.next();

                if (list.contains(item.getUnlocalizedName()))
                    validItems.add(item);
            }

            br.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            AchievementRewards.logger.info(validItems.size() + " Rewards Listed");
            AchievementRewards.logger.info("Achievement Rewards Pre-Initialized!");
        }


    }

    public static void init(File folder)
    {
        validItems = new ArrayList<Item>();
        File configLocation = new File(folder.getAbsoluteFile() + "/achievementrewards");
        file = new File(configLocation, "ARwhitelist.cfg");

        if (!configLocation.exists())
        {
            configLocation.mkdirs();
        }

        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                AchievementRewards.logger.warn("Error Initializing Achievement Rewards");
            }
        }

        loadValidItems();
    }
}
