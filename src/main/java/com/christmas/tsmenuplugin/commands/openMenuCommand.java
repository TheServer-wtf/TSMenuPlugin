package com.christmas.tsmenuplugin.commands;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.christmas.tsmenuplugin.managers.InventorySerialization.itemStackArrayFromBase64;

public class openMenuCommand implements CommandExecutor { // /openmenu buildermenu, this autogenerates overview

    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("utility.builder.openmenus")){
                if(args.length == 1) {
                    UUID pu = p.getUniqueId();
                    mainplugin.clearSelections(pu);
                    p.sendMessage(pf + ChatColor.GOLD + "Selections cleared");

                    System.out.println("Starting overview creation");
                    String selectedmenu = args[0];
                    Yaml yaml = new Yaml(selectedmenu + ".yml", mainplugin.getDataFolder() + "/GUIs/" + selectedmenu + "/");



                    Inventory overview = Bukkit.createInventory(null, 54, selectedmenu);

                    List<String> pagelist = yaml.getYamlEditor().readKeys();
                    List<String> pages = new ArrayList<>();
                    for(String ee : pagelist){
                        if(ee.contains("page")){ //clean the pages of crap
                            ee = ee.replaceAll("\\|-", "");
                            ee = ee.replaceAll(":", "");
                            ee = ee.replaceAll(" ", "");
                            pages.add(ee);

                        }
                    }
                    StringBuilder pagetest = new StringBuilder();
                    for(String pagetester : pages){
                        pagetest.append(pagetester + " ");
                    }
                    System.out.println("Pages: " + pagetest.toString());
                    System.out.println("Page List size:" + pages.size());
                    mainplugin.setPageList(pages, p.getUniqueId());
                    mainplugin.setCurMenuMap(p.getUniqueId(), selectedmenu);
                    int counter = 0;

                    //define none object
                    final ItemStack noneitem = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 64);
                    final ItemMeta meta = noneitem.getItemMeta();
                    meta.setDisplayName("\\uE004<none>\\uE004");
                    noneitem.setItemMeta(meta);


                    //add stuff into the overview menu
                    System.out.println("Started Overview for loop");
                    for(Object ef : pages){ // foreach iteration with the list
                        counter++;
                        String e = ef.toString();
                        System.out.println("OverViewLoop: Round "+counter);
                        //get the page and its details
                        Inventory getinv = Bukkit.createInventory(null, 54, "getterinv");

                        try {
                            ItemStack[] loadeditems = itemStackArrayFromBase64(yaml.get(e).toString());
                            for(ItemStack is : loadeditems) {
                                if (is == null) {
                                    is = noneitem;
                                }
                                getinv.addItem(is); // create inventory of the page

                            }
                            //no debug needed, nerd.
                            System.out.println("Round "+counter+". Finishing virtual Inventory");
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        if(getinv.getItem(0) != null) {
                            ItemStack representer = getinv.getItem(0); //get item at slot 54
                            ItemMeta reprometa = representer.getItemMeta();
                            List<String> itemlore = new ArrayList<>();
                            itemlore.add(e);
                            reprometa.setLore(itemlore);
                            representer.setItemMeta(reprometa);
                            overview.addItem(representer);
                            System.out.println("Le representer has arrived, welcome, "+ getinv.getItem(0).getItemMeta().getDisplayName());
                        }else{
                            System.out.println("some representer is null!!!! you fucking cunt");
                        }
                        //add details
                        System.out.println("Completed for loop for a page");
                    }
                    HumanEntity he = (HumanEntity) p;
                    he.openInventory(overview);
                    System.out.println("An Overview should've opened.");

                }
            }
        }

        return true;
    }
}
