package me.jiovannyalejos.advancedchorusfruit.commands;

import java.util.Map;

import me.jiovannyalejos.advancedchorusfruit.PluginData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            if(sender instanceof Player && PluginData
                    .getPermissions(((Player) sender).getUniqueId()).stream()
                    .noneMatch(p -> p.equals("remove_warps") || p.equals("all"))) {
                sender.sendMessage("Missing permissions");
                return false;
            }
        }
        if (args.length != 0) {
            int i = 0;
            Environment env;
            if (sender instanceof Player) {
                env = ((Player)sender).getWorld().getEnvironment();
            } else {
                if (args.length < 2) {
                    sender.sendMessage("Required dimension as argument, use /removelocation Overworld/Nether/End (location name)");
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
                        sender.sendMessage("This dimension doesn't exist, use /removelocation Overworld/Nether/End (location name)");
                        return false;
                }
                i = 1;
            }
            PluginData data = PluginData.getData();
            Map<String, String> locations = data.dimensions.get(env);

            StringBuilder argLocName;
            for(argLocName = new StringBuilder(); i < args.length; ++i) {
                argLocName.append(args[i]).append(" ");
            }

            if (locations.containsKey(argLocName.toString().trim())) {
                locations.remove(argLocName.toString().trim());
                data.dimensions.replace(env, locations);
                PluginData.writeData(data);
                sender.sendMessage(argLocName.append("has been removed").toString());
            } else {
                sender.sendMessage("No warp point found with name '" + argLocName.toString().trim() + "'");
            }
        } else {
            sender.sendMessage("Nothing removed; Warp point name required");
        }
        return true;
    }
}
