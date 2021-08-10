package me.JiovannyAlejos.advancedChorusFruit;

import org.bukkit.plugin.java.JavaPlugin;

import me.JiovannyAlejos.advancedChorusFruit.listeners.entityBurn;
import me.JiovannyAlejos.advancedChorusFruit.listeners.PlayerConsumeItem;
import me.JiovannyAlejos.advancedChorusFruit.commands.listLocations;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		getCommand("listlocations").setExecutor(new ListLocations(this));
		getServer().getPluginManager().registerEvents(new entityBurn(), this);
		getServer().getPluginManager().registerEvents(new PlayerConsumeItem(), this);
		getServer().getConsoleSender().sendMessage("plugin ready");
	}
}
