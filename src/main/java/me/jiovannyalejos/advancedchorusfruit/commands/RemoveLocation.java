package me.jiovannyalejos.advancedchorusfruit.commands;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class RemoveLocation implements CommandExecutor {
    private AdvancedChorusFruit plugin;
    public RemoveLocation(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("removelocation").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 0) {
            Gson gson = new Gson();
            World.Environment env;
            if(sender instanceof Player) {
                env = ((Player) sender).getWorld().getEnvironment();
            } else {
                env = World.Environment.NORMAL;
            }
            CoordinateData original = AdvancedChorusFruit.getData();
            Map<String, String> data = original.dimensions.get(env);
            String argLocName = "";
            for(String word : args) {
                argLocName += word + " ";
            }
            if(data.containsKey(argLocName.trim())) {
                data.remove(argLocName.trim());
                try {
                    Writer writer = new FileWriter(AdvancedChorusFruit.dataPath);
                    gson.toJson(CoordinateData.assignData(env, data, original), writer);
                    writer.close();
                    sender.sendMessage("Successfully removed warp location '" + argLocName.trim() + "'");
                } catch (IOException e) {e.printStackTrace();}
            } else sender.sendMessage("No warp location exists with the name '" + argLocName.trim() + "'");
        } else sender.sendMessage("Warp location name needed to remove it");
        return true;
    }
}
