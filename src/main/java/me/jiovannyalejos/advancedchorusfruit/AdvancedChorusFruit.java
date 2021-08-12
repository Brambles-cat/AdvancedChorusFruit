package me.jiovannyalejos.advancedchorusfruit;

import me.jiovannyalejos.advancedchorusfruit.commands.ListLocations;
import me.jiovannyalejos.advancedchorusfruit.commands.RemoveLocation;
import me.jiovannyalejos.advancedchorusfruit.listeners.EntityBurn;
import me.jiovannyalejos.advancedchorusfruit.listeners.PlayerTeleport;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedChorusFruit extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("listlocations").setExecutor(new ListLocations(this));
        getCommand("removelocation").setExecutor(new RemoveLocation(this));
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new EntityBurn(), this);
        getServer().getConsoleSender().sendMessage("plugin ready");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
