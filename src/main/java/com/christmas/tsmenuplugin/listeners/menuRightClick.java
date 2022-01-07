package com.christmas.tsmenuplugin.listeners;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.christmas.tsmenuplugin.managers.InventorySerialization.itemStackArrayFromBase64;

public class menuRightClick implements Listener {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null){ return;}
        Player p = (Player) e.getWhoClicked();
        UUID pu = p.getUniqueId();
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        if(p.hasPermission("utility.builder.openmenus")) {
            System.out.println("Access granted.");



            if(e.getView().getTitle().contains(mainplugin.getCurMenuMap(pu))) { // if the player has the right menu open
                System.out.println("Current Menu Title is equal to the Current Menu Map!");
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    ItemStack clickeditem = e.getCurrentItem();
                    //get the lore from clicked item
                    List<String> itemlore = clickeditem.getItemMeta().getLore();
                    StringBuilder lore = new StringBuilder();
                    for(String add : itemlore){
                        lore.append(add);
                    }
                    System.out.print("The item's lore is: //" + itemlore + "//, is this the name of a menu?");
                    //if the item lore is one of the pages ( representer )

                    if (mainplugin.getPageList(pu).contains(lore.toString())) {
                        System.out.println("ItemLore is inside the pages!");
                        final ItemStack noneitem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 64);
                        final ItemMeta meta = noneitem.getItemMeta();
                        meta.setDisplayName("<none>");
                        noneitem.setItemMeta(meta);
                        //get page and open it!
                        Inventory getinv = Bukkit.createInventory(null, 54, "getterinv");
                        Yaml yaml = new Yaml(mainplugin.getCurMenuMap(pu) + ".yml", mainplugin.getDataFolder() + "/GUIs/" + mainplugin.getCurMenuMap(pu) + "/");
                        try {
                            ItemStack[] loadeditems = itemStackArrayFromBase64(yaml.get(lore.toString()).toString());
                            for(ItemStack is : loadeditems) {
                                if (is == null) {
                                    is = noneitem;
                                }
                                getinv.addItem(is); // create inventory of the page

                            }
                                HumanEntity he = p;
                                he.openInventory(getinv);

                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }


                    }
                    e.setCancelled(true);
                }
            }
        }
    }
}
