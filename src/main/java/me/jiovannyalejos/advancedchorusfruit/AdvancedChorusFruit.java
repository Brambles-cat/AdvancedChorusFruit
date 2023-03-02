package me.jiovannyalejos.advancedchorusfruit;

import me.jiovannyalejos.advancedchorusfruit.commands.Permission;
import me.jiovannyalejos.advancedchorusfruit.commands.ListWarps;
import me.jiovannyalejos.advancedchorusfruit.commands.RemoveWarp;
import me.jiovannyalejos.advancedchorusfruit.listeners.AnvilPrepareListener;
import me.jiovannyalejos.advancedchorusfruit.listeners.EntityBurn;
import me.jiovannyalejos.advancedchorusfruit.listeners.ItemConsume;
import me.jiovannyalejos.advancedchorusfruit.listeners.PlayerTeleport;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedChorusFruit extends JavaPlugin {

    public void onEnable() {
        PluginData.dataFolderPath = this.getDataFolder().getPath();
        PluginData.dataPath = PluginData.dataFolderPath + "/PluginData.json";
        getCommand("listwarps").setExecutor(new ListWarps());
        getCommand("removewarp").setExecutor(new RemoveWarp());
        getCommand("permission").setExecutor(new Permission());
        this.getServer().getPluginManager().registerEvents(new AnvilPrepareListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemConsume(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        this.getServer().getPluginManager().registerEvents(new EntityBurn(), this);
        PluginData.playerPermissions = PluginData.getData().permissions;
        this.getServer().getConsoleSender().sendMessage("Advanced Chorus Fruit plugin ready");
    }

    public void onDisable() {}
}
