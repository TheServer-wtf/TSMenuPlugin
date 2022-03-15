package com.christmas.tsmenuplugin.commands;

import com.christmas.tsmenuplugin.TSMenuPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class getMenuBookCommand implements CommandExecutor {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1){
            if (p.hasPermission("utility.modeler.getmenubook")) {
                ItemStack newbook = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta bookmeta = newbook.getItemMeta();
                bookmeta.setDisplayName(args[0]);

                NamespacedKey cke = new NamespacedKey(mainplugin, "menu_book");
                PersistentDataContainer Bookcontainer = bookmeta.getPersistentDataContainer();
                if(Bookcontainer.has(cke, PersistentDataType.STRING)) {
                    String key = Bookcontainer.get(cke, PersistentDataType.STRING);
                }

                Bookcontainer.set(cke, PersistentDataType.STRING, args[0]);
                newbook.setItemMeta(bookmeta);

                p.getInventory().addItem(newbook);
                p.sendMessage(pf + ChatColor.GOLD + "You've been given the menubook for the menu "+ ChatColor.RED + args[0] + ChatColor.GOLD + ". Right click to use it!");
            }
        }else{
                p.sendMessage(pf + ChatColor.RED + "You're missing arguments! Correct usage: /getmenubook <menuname>");
    }

        }
        return true;

    }
}
