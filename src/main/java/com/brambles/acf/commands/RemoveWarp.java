package com.brambles.acf.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.brambles.acf.AdvancedChorusFruit;
import com.brambles.acf.PluginData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class RemoveWarp implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Environment env;

        int extraArgs = 0;

        if (sender instanceof Player player) {
            if (!PluginData.playerHasPermission(player, AdvancedChorusFruit.PluginPermission.REMOVE_WARPS)) {
                player.sendMessage("Missing permissions");
                return false;
            }

            if (args.length < 1) {
                sender.sendMessage("Warp point name required. Use /listwarps to see warp points");
                return false;
            }

            env = player.getWorld().getEnvironment();
        }
        else {
            if (!sender.isOp()) {
                sender.sendMessage("Missing permissions");
                return false;
            }

            if (args.length < 2) {
                sender.sendMessage("Dimension and warp point name required as arguments, use /removewarp Overworld/Nether/End (warp point name)");
                return false;
            }

            env = getEnvironment(args[0]);

            if (env == null) {
                sender.sendMessage("No data found for dimension \"" + args[0] + "\"");
                return false;
            }

            ++extraArgs;
        }

        Map<String, String> warpPoints = PluginData.getWarpPoints(env);

        StringBuilder argWarpPointName = new StringBuilder();
        for (int i = extraArgs; i < args.length; ++i)
            argWarpPointName.append(args[i]).append(" ");

        String warpPointName = argWarpPointName.toString().trim();
        if (warpPoints.containsKey(warpPointName)) {
            warpPoints.remove(warpPointName);
            sender.sendMessage(argWarpPointName.append("successfully removed").toString());
            return true;
        }

        sender.sendMessage("No warp point found with name \"" + warpPointName + "\"");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        boolean isPlayer = sender instanceof Player;

        if (args.length != (isPlayer ? 1 : 2)) return completions;

        Environment env = isPlayer ? ((Player) sender).getWorld().getEnvironment() : getEnvironment(args[0]);

        PluginData.getWarpPoints(env).forEach((pointName, location) -> completions.add(pointName));

        String currentArg = args[isPlayer ? 0 : 1];
        completions.removeIf(str -> !str.startsWith(currentArg));
        return completions;
    }

    private Environment getEnvironment(String envName) {
        return switch(envName.toLowerCase()) {
            case "overworld" -> Environment.NORMAL;
            case "nether" -> Environment.NETHER;
            case "end" -> Environment.THE_END;
            default -> null;
        };
    }
}
