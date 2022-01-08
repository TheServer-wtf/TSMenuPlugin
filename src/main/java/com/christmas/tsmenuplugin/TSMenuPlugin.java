package com.christmas.tsmenuplugin;

import com.christmas.tsmenuplugin.commands.*;
import com.christmas.tsmenuplugin.listeners.menuRightClick;
import com.christmas.tsmenuplugin.listeners.rightClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.christmas.tsmenuplugin.listeners.invClickEvent;
import com.christmas.tsmenuplugin.listeners.onJoinEvent;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class TSMenuPlugin extends JavaPlugin {
    String prefix = ChatColor.GRAY + "[" + ChatColor.RESET + "\uE004" + ChatColor.GRAY + "] " + ChatColor.WHITE;
    private static TSMenuPlugin plugin = null;

    //HashMaps:

    //selectedguimap
    public HashMap<UUID, String> selectedGUIMap = new HashMap<>();

    public void setGUIMap(UUID pu, String s) {
        selectedGUIMap.put(pu, s);
    }

    public String getGUIMap(UUID pu) {
        return selectedGUIMap.get(pu);
    }

    //selectedguimap end
//selectedpagemap
    public HashMap<UUID, String> selectedpageMap = new HashMap<>();

    public void setPageMap(UUID pu, String s) {
        selectedpageMap.put(pu, s);
    }

    public String getPageMap(UUID pu) {
        return selectedpageMap.get(pu);
    }

    //selectedpagemap end
//editingmap
    public HashMap<UUID, Boolean> editingPageMap = new HashMap<>();

    public void setEditingMap(UUID pu, Boolean b) {
        editingPageMap.put(pu, b);
    }

    public Boolean getEditingMap(UUID pu) {
        return editingPageMap.get(pu);
    }
//editingmap end
//pagesmap
    public HashMap<UUID, List<String>> pagesListMap = new HashMap<>();
    public List<String> getPageList(UUID pu){return pagesListMap.get(pu);}
    public void setPageList(List<String> list, UUID pu){pagesListMap.put(pu, list);}
//pagsemap end
//playercurrentmenumap
    public HashMap<UUID, String> pCurMenuMap = new HashMap<>();
    public void setCurMenuMap(UUID pu, String s) {
        pCurMenuMap.put(pu, s);
    }
    public String getCurMenuMap(UUID pu) {
        return pCurMenuMap.get(pu);
    }
    public void clearSelections(UUID pu){
        selectedGUIMap.remove(pu);
        selectedpageMap.remove(pu);
    }
//playercurrentmenumap end

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("Build Menu Plugin initialized! :D");
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("getmenu").setExecutor(new createMenuCommand());
        getCommand("openmenu").setExecutor(new openMenuCommand());
        getCommand("editpage").setExecutor(new editPageCommand());
        getCommand("getmenubook").setExecutor(new getMenuBookCommand());
        getCommand("delpage").setExecutor(new delPageCommand());
        getServer().getPluginManager().registerEvents(new rightClickEvent(), this);
        getServer().getPluginManager().registerEvents(new invClickEvent(), this);
        getServer().getPluginManager().registerEvents(new onJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new menuRightClick(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TSMenuPlugin getPlugin() {
        return plugin;
    }

    public String getPrefix() {
        return prefix;
    }
}

