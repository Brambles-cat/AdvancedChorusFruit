package me.jiovannyalejos.advancedchorusfruit.commands;

import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetOpExclusive implements CommandExecutor {
    private AdvancedChorusFruit plugin;

    public SetOpExclusive(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("setopexclusive").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(sender.isOp());
        if (sender.isOp() && args.length != 0) {
            CoordinateData data = AdvancedChorusFruit.getData();
            if (args[0].equalsIgnoreCase("true")) {
                data.adminExclusive = true;
            } else {
                if (!args[0].equalsIgnoreCase("false")) {
                    return false;
                }

                data.adminExclusive = false;
            }

            sender.sendMessage("Operator exclusive setting and removing warp locations set to " + args[0].toLowerCase());
            AdvancedChorusFruit.writeData(data);
        }

        return false;
    }
}
