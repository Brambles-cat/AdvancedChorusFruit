package me.jiovannyalejos.advancedchorusfruit.commands;

import me.jiovannyalejos.advancedchorusfruit.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Permission implements CommandExecutor, TabCompleter {
    private final String[] permissions = {
            "all",
            "set_warps",
            "remove_warps",
            "no_warping"
    };
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {sender.sendMessage("Missing permissions"); return false;}
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {sender.sendMessage("Player not found"); return false;}
        UUID playerId = target.getUniqueId();
        List<String> targetPermissions = PluginData.getPermissions(playerId);
        if(args[0].equalsIgnoreCase("list")) {
            StringBuilder builder = new StringBuilder(target.getName()).append(" permissions:");
            for(String permission : targetPermissions) builder.append("\n - ").append(permission);
            sender.sendMessage(builder.toString());
            return true;
        }
        if(target.isOp()) {sender.sendMessage("Target is an operator"); return false;}
        // separate in order to prevent unnecessary json parsing

        if(args.length < 3) {sender.sendMessage("A permission must be specified"); return false;}
        PluginData data = PluginData.getData();
        String permissionArg = args[2].toLowerCase();
        if(args[0].equalsIgnoreCase("give")) {
            if (permissionCheck(sender, permissionArg)) return false;
            if (!targetPermissions.contains(permissionArg)) {
                data.permissions.get(playerId).add(permissionArg);
                PluginData.playerPermissions.get(playerId).add(permissionArg);
            }
        } else if(args[0].equalsIgnoreCase("remove")) {
            if (permissionCheck(sender, permissionArg)) return false;
            data.permissions.get(playerId).remove(permissionArg);
            PluginData.playerPermissions.get(playerId).remove(permissionArg);
        }
        else {sender.sendMessage("Must specify permission action"); return  false;}
        PluginData.writeData(data);
        sender.sendMessage("Permissions for " + target.getName() + " updated");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("give");
            completions.add("remove");
            completions.add("list");
        } else if (args.length == 2) {
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
        } else if (args.length == 3) {
            for(String permission : permissions) completions.add(permission);
        }
        String currentArg = args[args.length - 1];
        completions.removeIf(str -> !str.startsWith(currentArg));
        return completions;
    }

    private boolean permissionCheck(CommandSender sender, String permission) {
        if(Arrays.stream(permissions).noneMatch(str -> str.equals(permission))) {
            sender.sendMessage("Permission \"" + permission + "\" doesn't exist. Use /help permission for a list");
            return true;
        }
        return false;
    }
}
