package com.brambles.acf.commands;

import com.brambles.acf.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.brambles.acf.AdvancedChorusFruit.PluginPermission;

public class Permission implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) { sender.sendMessage("Missing permissions"); return false; }
        if (args.length < 1) { sender.sendMessage("No permission target specified"); return false; }

        List<String> targetPermissions;

        Player playerTarget = Bukkit.getPlayer(args[0]);
        if (playerTarget == null && !args[0].equalsIgnoreCase("default")) { sender.sendMessage("Player not found"); return false; }

        String targetName = playerTarget != null ? playerTarget.getName() : "Default";
        targetPermissions = playerTarget != null ? PluginData.getPermissions(playerTarget.getUniqueId()) : PluginData.getPermissions();

        if (args.length < 2) return false;

        if (args[1].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder(targetName).append(" permissions:");
            for(String permission : targetPermissions) builder.append("\n - ").append(permission);
            sender.sendMessage(builder.toString());
            return true;
        }

        if (playerTarget != null && playerTarget.isOp()) { sender.sendMessage("Target is an operator"); return false; }

        PluginPermission permission = PluginPermission.get(args[1]);

        if (permission == null) { sender.sendMessage("Permission \"" + args[1] + "\" doesn't exist"); return false; }
        if (args.length < 3) { sender.sendMessage("Incomplete command"); return false; }

        boolean allowed = args[2].equalsIgnoreCase("allow");
        
        if (!allowed && !args[2].equalsIgnoreCase("disallow")) { sender.sendMessage("Invalid argument \"" + args[2] + "\""); return false; }

        if (playerTarget != null)
            PluginData.updatePermissions(playerTarget.getUniqueId(), permission, allowed);
        else
            PluginData.updateDefaultPermission(permission, allowed);

        sender.sendMessage(playerTarget != null ? "Permissions for " + playerTarget.getName() + " updated" : "Default permissions updated");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("edit");
            completions.add("remove");
            completions.add("list");
        } else if (args.length == 2) {
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
        } else if (args.length == 3) {
            for (PluginPermission permission : PluginPermission.values()) completions.add(permission.id());
        }
        String currentArg = args[args.length - 1];
        completions.removeIf(str -> !str.startsWith(currentArg));
        return completions;
    }
}