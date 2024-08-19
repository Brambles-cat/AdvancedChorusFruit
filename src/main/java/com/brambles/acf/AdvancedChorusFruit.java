package com.brambles.acf;

import com.brambles.acf.commands.ListWarps;
import com.brambles.acf.commands.Permission;
import com.brambles.acf.commands.RemoveWarp;
import com.brambles.acf.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AdvancedChorusFruit extends JavaPlugin {
    public enum PluginPermission {
        SET_WARPS,
        REMOVE_WARPS,
        LIST_WARPS,
        USE_WARPS;

        public String id() {
            return name().toLowerCase();
        }

        public static PluginPermission get(String permissionString) {
            try { return PluginPermission.valueOf(permissionString.toUpperCase()); }
            catch (IllegalArgumentException e) { return null; }
        }
    }

    @Override
    public void onEnable() {
        PluginData.dataFolderPath = this.getDataFolder().getPath();
        PluginData.dataPath = PluginData.dataFolderPath + "/PluginData.json";

        getCommand("listwarps").setExecutor(new ListWarps());
        getCommand("removewarp").setExecutor(new RemoveWarp());
        getCommand("permission").setExecutor(new Permission());

        getServer().getPluginManager().registerEvents(new AnvilPrepare(), this);
        getServer().getPluginManager().registerEvents(new ItemConsume(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new EntityBurn(), this);
        getServer().getPluginManager().registerEvents(new ItemDropped(this), this);
        getServer().getPluginManager().registerEvents(new ItemPickedUp(), this);

        try {
            PluginData.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.getServer().getConsoleSender().sendMessage("Advanced Chorus Fruit plugin ready");
    }


    @Override
    public void onDisable() {
        PluginData.writeData();
    }
}
