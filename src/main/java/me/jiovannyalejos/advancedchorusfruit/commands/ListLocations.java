package me.jiovannyalejos.advancedchorusfruit.commands;

import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import me.jiovannyalejos.advancedchorusfruit.Dimension;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListLocations implements CommandExecutor {
    private AdvancedChorusFruit plugin;
    public ListLocations(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("listlocations").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use commands for this plugin");
            return false;
        }
        CoordinateData data = AdvancedChorusFruit.getData();
        World.Environment env = ((Player) sender).getWorld().getEnvironment();
        Dimension dimData = CoordinateData.getDimData(env, data);
        String coordList = "No warp locations set";
        if(dimData.coordinates.size() != 0) {
            coordList = "List of warp locations in the " + CoordinateData.format(env) + "\n";
            ArrayList<String> names = dimData.locNames;
            for(int i = 0; i < dimData.coordinates.size(); i++) {
                String[] coordinates = dimData.coordinates.get(i).split(Pattern.quote("|"));
                coordList += "\n" + names.get(i) + ": " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2];
            }
        }
        Player p = (Player) sender;
        p.sendMessage(coordList);
        return true;
    }
}
