package com.christmas.tsmenuplugin.listeners;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class rightClickEvent implements Listener {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player p = e.getPlayer();
            ItemStack handeditem = p.getInventory().getItemInMainHand();
            if(handeditem.getType() == Material.ENCHANTED_BOOK){
                if(handeditem.getItemMeta().getDisplayName().contains("menu_book")) {
                    if (p.hasPermission("utility.builder.openmenus")) { // vibe check
                        StringBuilder menuname = new StringBuilder();
                        menuname.append(handeditem.getItemMeta().getDisplayName());
                        menuname.delete(0, 10); // only keep menuname
                        p.sendMessage(pf + ChatColor.GOLD + "Menu " + menuname.toString() + " opened. Have fun!");
                        p.performCommand("openmenu "+menuname.toString());
                    }
                }
            }
        }
    }
}
