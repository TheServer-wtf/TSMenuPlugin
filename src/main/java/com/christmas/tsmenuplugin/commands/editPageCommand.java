package com.christmas.tsmenuplugin.commands;


import com.christmas.tsmenuplugin.TSMenuPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class editPageCommand implements CommandExecutor { // /editpage misc1
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("utility.modeler.editpage")){
                if(args.length == 1){
                    UUID pu = p.getUniqueId();
                    if(!mainplugin.getGUIMap(pu).isEmpty()){
                        String pagename;
                        if(!args[0].contains("page")) {
                            pagename = args[0] + "page";
                        }else{
                            pagename = args[0];
                        }
                        mainplugin.setEditingMap(pu, true); //set editing to true
                        mainplugin.setPageMap(pu, pagename);
                        p.sendMessage(pf + ChatColor.YELLOW + "Selected Page: "+ ChatColor.RED + pagename + ChatColor.GRAY + " (This Command Auto-adds the word page to a page. Sorry but it has to be that way)");
                        p.sendMessage(pf + ChatColor.YELLOW + "You can now edit your page and click the editor item in your inventory to save it.");
                    }else{
                        p.sendMessage(pf + ChatColor.RED + "It seems like you're not editing any menus. Use /getmenu <Menuname> first");
                    }


                }else{
                    p.sendMessage(pf + ChatColor.RED + "You are missing arguments. Correct usage: /editpage <page name here>. This command edits pages, and creates them automatically!");
                }
            }
        }

        return true;
    }
}
