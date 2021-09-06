package me.jiovannyalejos.advancedchorusfruit.commands;

import com.google.common.base.FinalizableReferenceQueue;
import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class RemoveLocation implements CommandExecutor {
    private AdvancedChorusFruit plugin;
    public RemoveLocation(AdvancedChorusFruit plugin) {
        this.plugin = plugin;
        plugin.getCommand("removelocation").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        System.out.println(Arrays.toString(args));
        if(args.length != 0) {
            Gson gson = new Gson();
            CoordinateData data = AdvancedChorusFruit.getData();
            String argLocName = "";
            for(String word : args) {
                argLocName += word + " ";
            }
            int index = data.locNames.indexOf(argLocName.trim());
            if(data.locNames.contains(argLocName.trim())) {
                data.coordinates.remove(index);
                data.locNames.remove(index);
                try {
                    Writer writer = new FileWriter(AdvancedChorusFruit.dataPath);
                    gson.toJson(data, writer);
                    writer.close();
                    sender.sendMessage("Successfully removed warp location '" + argLocName.trim() + "'");
                } catch (IOException e) {e.printStackTrace();}
            } else sender.sendMessage("No warp location exists with the name '" + argLocName.trim() + "'");
        } else sender.sendMessage("Warp location name needed to remove it");
        return true;
    }
}
