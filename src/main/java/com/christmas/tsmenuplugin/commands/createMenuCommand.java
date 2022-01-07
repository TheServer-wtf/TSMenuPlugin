package com.christmas.tsmenuplugin.commands;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class createMenuCommand implements CommandExecutor { // /getmenu AmongUsGUI
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("utility.modeler.getmenu")){
                if(args.length ==1){
                    UUID pu = p.getUniqueId();
                    String menuname = args[0];
                    //this creates the menueditor item
                    final ItemStack menueditor = new ItemStack(Material.GLOW_BERRIES, 1);
                    final ItemMeta meta = menueditor.getItemMeta();
                    meta.setDisplayName("menu_editor "+ menuname);
                    menueditor.setItemMeta(meta);
                    //set maps
                    mainplugin.setGUIMap(pu, menuname);
                    Yaml yaml = new Yaml(menuname + ".yml", mainplugin.getDataFolder() + "/GUIs/" + menuname + "/");
                    p.sendMessage(pf + ChatColor.GOLD + "Menu " + menuname + " has been created. Edit and create pages in it now with /editpage <pagename>");
                    p.sendMessage(pf + ChatColor.RED + "You have been given the item editor for this Menu. Use it like the tutorial (Garry) tells you to!");
                    p.getInventory().addItem(menueditor);

                }
            }
        }

        return true;
    }
}
