package me.jiovannyalejos.advancedchorusfruit.commands;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.io.Reader;
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
        String coordList = "List of warp locations\n";
        if(data.coordinates.size() == 0) {
            coordList = "No warp locations set";
        } else {
            ArrayList<String> names = data.locNames;
            for(int i = 0; i < data.coordinates.size(); i++) {
                String[] coordinates = data.coordinates.get(i).split(Pattern.quote("|"));
                coordList += "\n" + names.get(i) + ", " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2];
            }
        }
        Player p = (Player) sender;
        p.sendMessage(coordList);
        return true;
    }
}
