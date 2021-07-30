package me.JiovannyAlejos.advancedChorusFruit.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.JiovannyAlejos.advancedChorusFruit.Main;

public class listLocations implements CommandExecutor {
	@SuppressWarnings("unused")
	private Main plugin;
	public listLocations(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("listlocations").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only players can use commands for this plugin");
			return true; // returning true means
		}
		Player p = (Player) sender;
		p.setGameMode(GameMode.CREATIVE);
		p.sendMessage("test");
		return false;
	}
}