package com.christmas.tsmenuplugin.listeners;


import com.christmas.tsmenuplugin.TSMenuPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class onJoinEvent implements Listener {
    TSMenuPlugin mainplugin = TSMenuPlugin.getPlugin();
    String pf = mainplugin.getPrefix();


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID pu = p.getUniqueId();
        if(p.hasPermission("group.modeler") ||p.hasPermission("utility.modeler.getmenu")){
            mainplugin.setEditingMap(pu, false);

        }
    }
}
