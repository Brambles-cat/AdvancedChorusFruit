package com.brambles.acf.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.brambles.acf.PluginData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import static com.brambles.acf.AdvancedChorusFruit.PluginPermission;

public class ListWarps implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!((sender instanceof Player player && PluginData.playerHasPermission(player, PluginPermission.LIST_WARPS)) || sender.isOp())) {
            sender.sendMessage("Missing permissions");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Dimension required as an argument, use /listwarps Overworld/Nether/End");
            return false;
        }

        Environment env;

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
                sender.sendMessage("No data found for dimension \"" + args[0] + "\"");
                return false;
        }

        Map<String, String> warpPoints = PluginData.getWarpPoints(env);

        if (!warpPoints.isEmpty()) {
            StringBuilder coordList = new StringBuilder("List of warp points in the " + PluginData.format(env) + "\n");

            for(String key : warpPoints.keySet()) {
                String[] coordinates = warpPoints.get(key).split(Pattern.quote("|"));
                coordList.append("\n").append(key).append(": ").append(coordinates[0]).append(", ").append(coordinates[1]).append(", ").append(coordinates[2]);
            }

            sender.sendMessage(coordList.toString());
        }
        else sender.sendMessage("No warp points set");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) return new ArrayList<>();

        List<String> completions = new ArrayList<>();
        completions.add("overworld");
        completions.add("nether");
        completions.add("end");

        String currentArg = args[args.length - 1];
        completions.removeIf(str -> !str.startsWith(currentArg));
        return completions;
    }
}