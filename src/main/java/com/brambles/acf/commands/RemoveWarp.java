package com.brambles.acf.commands;

import java.util.Map;

import com.brambles.acf.AdvancedChorusFruit;
import com.brambles.acf.PluginData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp implements CommandExecutor {
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

            String dimensionName = args[0].toLowerCase();
            switch(dimensionName) {
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
}
