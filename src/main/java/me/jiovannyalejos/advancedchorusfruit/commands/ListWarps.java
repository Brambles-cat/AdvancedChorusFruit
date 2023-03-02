package me.jiovannyalejos.advancedchorusfruit.commands;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import me.jiovannyalejos.advancedchorusfruit.PluginData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListWarps implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Environment env;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            env = player.getWorld().getEnvironment();
            List<String> permissions = PluginData.getPermissions(player.getUniqueId());
            if(!(sender.isOp() || permissions.contains("listing") || permissions.contains("all"))) {
                sender.sendMessage("Missing permissions");
                return false;
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage("Required dimension as an argument, use /listwarps Overworld/Nether/End");
                return false;
            }

            String dimension = args[0].toLowerCase();
            switch(dimension) {
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
        Map<String, String> warpPoints = PluginData.getData().dimensions.get(env);
        StringBuilder coordList = new StringBuilder("No warp points set");
        if (!warpPoints.isEmpty()) {
            coordList = new StringBuilder("List of warp points in the " + PluginData.format(env) + "\n");

            for(String key : warpPoints.keySet()) {
                String[] coordinates = warpPoints.get(key).split(Pattern.quote("|"));
                coordList.append("\n").append(key).append(": ").append(coordinates[0]).append(", ").append(coordinates[1]).append(", ").append(coordinates[2]);
            }
        }

        sender.sendMessage(coordList.toString());
        return true;
    }
}