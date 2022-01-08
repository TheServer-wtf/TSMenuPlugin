package com.christmas.tsmenuplugin.listeners;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class rightClickEvent implements Listener {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player p = e.getPlayer();
            ItemStack handeditem = p.getInventory().getItemInMainHand();
            if (p.hasPermission("utility.builder.openmenus")) { // vibe check
                if (handeditem.getType() == Material.ENCHANTED_BOOK) {
                    NamespacedKey cke = new NamespacedKey(mainplugin, "menu_book");
                    PersistentDataContainer Bookcontainer = handeditem.getItemMeta().getPersistentDataContainer();
                    if (Bookcontainer.has(cke, PersistentDataType.STRING)) {
                        String menuname = Bookcontainer.get(cke, PersistentDataType.STRING);
                        System.out.println("opened menu" + menuname);
                        p.sendMessage(pf + ChatColor.GOLD + "Menu " + menuname + " opened. Have fun!");
                        p.performCommand("openmenu " + menuname);
                    }
                }
            }
        }
    }
}
