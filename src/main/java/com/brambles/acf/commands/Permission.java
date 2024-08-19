package com.brambles.acf.commands;

import com.brambles.acf.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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
        targetPermissions = playerTarget != null ? PluginData.getPermissions(playerTarget) : PluginData.getPermissions();

        if (args.length < 2) return false;

        if (args[1].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder(targetName).append(" permissions:");

            for(String permission : targetPermissions)
                builder.append("\n - ").append(permission);

            sender.sendMessage(builder.toString());
            return true;
        }

        if (playerTarget != null && playerTarget.isOp()) { sender.sendMessage("Target is an operator"); return false; }

        PluginPermission permission = PluginPermission.get(args[1]);

        if (permission == null) { sender.sendMessage("Permission \"" + args[1] + "\" doesn't exist"); return false; }
        if (args.length < 3) { sender.sendMessage("Incomplete command"); return false; }

        switch (args[2].toLowerCase()) {
            case "allow":
                updatePermission(playerTarget, permission, true);
                break;
            case "disallow":
                updatePermission(playerTarget, permission, false);
                break;
            case "reset":
                if (playerTarget == null) {
                    sender.sendMessage("Resetting is only for player permissions");
                    return false;
                }
                PluginData.resetPermissions(playerTarget.getUniqueId());
                break;
            default:
                sender.sendMessage("Invalid argument \"" + args[2] + "\"");
                return false;
        }

        sender.sendMessage(playerTarget != null ? "Permissions for " + playerTarget.getName() + " updated" : "Default permissions updated");
        return true;
    }

    private static void updatePermission(Player target, PluginPermission permission, boolean allowed) {
        if (target != null)
            PluginData.updatePermissions(target.getUniqueId(), permission, allowed);
        else
            PluginData.updateDefaultPermission(permission, allowed);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        switch (args.length) {
            case 1:
                completions.add("default");
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
                break;
            case 2:
                completions.add("list");
                Arrays.stream(PluginPermission.values()).forEach(permission -> completions.add(permission.id()));
                break;
            case 3:
                if (args[1].equalsIgnoreCase("list"))
                    return completions;

                completions.add("allow");
                completions.add("disallow");

                if (!args[0].equalsIgnoreCase("default"))
                    completions.add("reset");
                break;
            default:
                return completions;
        }

        String currentArg = args[args.length - 1];
        completions.removeIf(str -> !str.startsWith(currentArg));
        return completions;
    }
}