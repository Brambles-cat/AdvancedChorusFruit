package me.jiovannyalejos.advancedchorusfruit.commands;

import java.util.Map;
import java.util.regex.Pattern;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListLocations implements CommandExecutor {
    private AdvancedChorusFruit plugin;

    public ListLocations(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("listlocations").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Environment env;
        if (sender instanceof Player) {
            env = ((Player)sender).getWorld().getEnvironment();
        } else {
            if (args.length == 0) {
                sender.sendMessage("Required dimension as an argument, use /listlocations Overworld/Nether/End");
                return false;
            }

            String data = args[0].toLowerCase();
            switch(data) {
                case "overworld":
                    env = Environment.NORMAL;
                    break;
                case "nether":
                    env = Environment.NETHER;
                    break;
                case "end":
                    env = Environment.THE_END;
                    break;
                default:
                    sender.sendMessage("This dimension doesn't exist, choose \"Overworld\", \"Nether\", or \"End\"");
                    return false;
            }
        }

        Map<String, String> data = AdvancedChorusFruit.getData().dimensions.get(env);
        StringBuilder coordList = new StringBuilder("No warp locations set");
        if (!data.isEmpty()) {
            coordList = new StringBuilder("List of warp locations in the " + CoordinateData.format(env) + "\n");

            for(String key : data.keySet()) {
                String[] coordinates = data.get(key).split(Pattern.quote("|"));
                coordList.append("\n").append(key).append(": ").append(coordinates[0]).append(", ").append(coordinates[1]).append(", ").append(coordinates[2]);
            }
        }

        sender.sendMessage(coordList.toString());
        return true;
    }
}