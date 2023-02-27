package me.jiovannyalejos.advancedchorusfruit.commands;

import java.util.Map;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveLocation implements CommandExecutor {
    private AdvancedChorusFruit plugin;

    public RemoveLocation(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("removelocation").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CoordinateData original = AdvancedChorusFruit.getData();
        if (original.adminExclusive && !sender.isOp()) {
            sender.sendMessage("Missing operator permissions");
            return true;
        } else {
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

                Map<String, String> data = original.dimensions.get(env);

                StringBuilder argLocName;
                for(argLocName = new StringBuilder(); i < args.length; ++i) {
                    argLocName.append(args[i]).append(" ");
                }

                if (data.containsKey(argLocName.toString().trim())) {
                    data.remove(argLocName.toString().trim());
                    AdvancedChorusFruit.writeData(env, data, original);
                    sender.sendMessage(argLocName.append("has been successfully removed").toString());
                } else {
                    sender.sendMessage("No warp location exists with the name '" + argLocName.toString().trim() + "'");
                }
            } else {
                sender.sendMessage("Warp location name needed to remove it");
            }

            return true;
        }
    }
}
