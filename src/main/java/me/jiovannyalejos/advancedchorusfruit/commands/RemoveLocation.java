package me.jiovannyalejos.advancedchorusfruit.commands;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            Reader reader = null;
            try {
                reader = Files.newBufferedReader(Paths.get("C:\\Users\\gaela\\IdeaProjects\\AdvancedChorusFruit\\src\\main\\java\\me\\jiovannyalejos\\advancedchorusfruit\\tpData.json"));
            } catch (IOException e) {e.printStackTrace();}
            CoordinateData data = gson.fromJson(reader, CoordinateData.class);
            String argLocName = "";
            for(String word : args) {
                argLocName += word + " ";
            }
            int index = data.locNames.indexOf(argLocName.trim());
            if(data.locNames.contains(argLocName.trim())) {
                Writer writer = null;
                try {
                    data.coordinates.remove(index);
                    data.locNames.remove(index);
                    writer = new FileWriter("C:\\Users\\gaela\\IdeaProjects\\AdvancedChorusFruit\\src\\main\\java\\me\\jiovannyalejos\\advancedchorusfruit\\tpData.json");
                    gson.toJson(data, writer);
                    writer.close();
                    reader.close();
                    sender.sendMessage("Successfully removed warp location '" + argLocName.trim() + "'");
                } catch (IOException e) {e.printStackTrace();}
            } else sender.sendMessage("No warp location exists with the name '" + argLocName.trim() + "'");
        } else sender.sendMessage("Warp location name needed to remove it");
        return true;
    }
}
