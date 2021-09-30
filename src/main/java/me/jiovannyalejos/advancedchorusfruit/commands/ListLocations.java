package me.jiovannyalejos.advancedchorusfruit.commands;

import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ListLocations implements CommandExecutor {
    private AdvancedChorusFruit plugin;
    public ListLocations(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("listlocations").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        World.Environment env;
        if(sender instanceof Player) {
            env = ((Player) sender).getWorld().getEnvironment();
        } else {
            env = World.Environment.NORMAL;
        }
        Map<String, String> data = AdvancedChorusFruit.getData().dimensions.get(env);
        String coordList = "No warp locations set";
        if(!data.isEmpty()) {
            coordList = "List of warp locations in the " + CoordinateData.format(env) + "\n";
            Set<String> names = data.keySet();
            for(String key : names) {
                String[] coordinates = data.get(key).split(Pattern.quote("|"));
                coordList += "\n" + key + ": " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2];
            }
        }
        sender.sendMessage(coordList);
        return true;
    }
}
