package com.christmas.tsmenuplugin.listeners;


import com.christmas.tsmenuplugin.TSMenuPlugin;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com.christmas.tsmenuplugin.managers.InventorySerialization.itemStackArrayToBase64;

public class invClickEvent implements Listener {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @EventHandler
    public void oninvClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null){ return;}
        if(e.getClickedInventory().getType() != InventoryType.CHEST){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        UUID pu = p.getUniqueId();
        if(p.hasPermission("utility.builder.menu")){
            if(mainplugin.getEditingMap(pu) == null){
                mainplugin.setEditingMap(pu, false);
            }

            if(mainplugin.getEditingMap(pu)){
                //get clicked item

                ItemStack clickeditem = e.getCurrentItem();
                if(clickeditem != null){
                    if(clickeditem.getItemMeta().getDisplayName().contains("menu_editor ")){
                        e.setCancelled(true);
                        System.out.println("menueditor detected, now checking for openedinventory");
                        if(p.getOpenInventory().getType() == InventoryType.CHEST) {
                            String selectedmenu = mainplugin.getGUIMap(pu);
                            String selectedpage = mainplugin.getPageMap(pu);
                            Yaml yaml = new Yaml(selectedmenu + ".yml", mainplugin.getDataFolder() + "/GUIs/" + selectedmenu + "/");

                            String serializedinv = itemStackArrayToBase64(e.getClickedInventory().getContents());
                            yaml.set(selectedpage, serializedinv);
                            p.sendMessage(pf + ChatColor.GOLD + "Set the currently opened doublechest as page named " + selectedpage + " in menu "+selectedmenu);
                        }else{
                            System.out.println("Player isn't viewing a chest-type inventory");
                        }
                    }else{
                        System.out.println("Item isn't a menueditor");
                    }
                }else{
                    System.out.println("Clicked item is null");
                }
            }
        }
    }
}
