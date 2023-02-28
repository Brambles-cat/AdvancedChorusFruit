package me.jiovannyalejos.advancedchorusfruit.commands;

import java.util.Map;
import java.util.regex.Pattern;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.Data;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListWarps implements CommandExecutor {
    private AdvancedChorusFruit plugin;

    public ListWarps(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("listwarps").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Environment env;
        Data data = AdvancedChorusFruit.getData();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            env = player.getWorld().getEnvironment();
            /*if(!data.permissions.containsKey(player.getUniqueId()) || !data.permissions.get(player.getUniqueId()).contains("listwarps")) {
                sender.sendMessage("Missing permissions");
                return true;
            }*/
        } else {
            if (args.length == 0) {
                sender.sendMessage("Required dimension as an argument, use /listlocations Overworld/Nether/End");
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
        Map<String, String> warpPoints = data.dimensions.get(env);
        StringBuilder coordList = new StringBuilder("No warp locations set");
        if (!warpPoints.isEmpty()) {
            coordList = new StringBuilder("List of warp locations in the " + Data.format(env) + "\n");

            for(String key : warpPoints.keySet()) {
                String[] coordinates = warpPoints.get(key).split(Pattern.quote("|"));
                coordList.append("\n").append(key).append(": ").append(coordinates[0]).append(", ").append(coordinates[1]).append(", ").append(coordinates[2]);
            }
        }

        sender.sendMessage(coordList.toString());
        return true;
    }
}