package com.christmas.tsmenuplugin.commands;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class delPageCommand implements CommandExecutor {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("utility.modeler.delpage")) {
                if (args.length == 1){
                    UUID pu = p.getUniqueId();
                if (mainplugin.getEditingMap(pu)) {
                    if (mainplugin.getCurMenuMap(pu) == null) {
                        p.sendMessage(pf + ChatColor.RED + "No Menu selected! Select one with /getmenu");
                        return true;
                    }
                    String selectedpage = args[0];

                    String selectedmenu = mainplugin.getCurMenuMap(pu);
                    Yaml yaml = new Yaml(selectedmenu + ".yml", mainplugin.getDataFolder() + "/GUIs/" + selectedmenu + "/");
                    if (yaml.get(selectedpage).equals("")) {
                        p.sendMessage(pf + ChatColor.GOLD + "Page is empty!");
                        return true;
                    }
                    yaml.remove(selectedpage);
                    p.sendMessage(pf + ChatColor.GOLD + "Removed Page " + selectedpage + " in Menu " + selectedmenu);
                }
            }else{
                p.sendMessage(pf + ChatColor.RED + "invalid usage! /delpage <pagename> (while having a menu selected using /getmenu)");
                }
            }
        }

        return true;

    }
}
