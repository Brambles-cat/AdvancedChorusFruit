package me.jiovannyalejos.advancedchorusfruit;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.commands.ListLocations;
import me.jiovannyalejos.advancedchorusfruit.commands.RemoveLocation;
import me.jiovannyalejos.advancedchorusfruit.listeners.AnvilPrepareListener;
import me.jiovannyalejos.advancedchorusfruit.listeners.EntityBurn;
import me.jiovannyalejos.advancedchorusfruit.listeners.PlayerTeleport;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class AdvancedChorusFruit extends JavaPlugin {
    public static String dataPath;
    static String dataFolderPath;
    static Gson gson = new Gson();
    static Reader reader;

    public static CoordinateData getData() {
        try {
            if(!(Files.exists(Paths.get(dataPath)))) {
                Files.createDirectory(Paths.get(dataFolderPath));
                Files.createFile(Paths.get(dataPath));
                Writer writer = new FileWriter(AdvancedChorusFruit.dataPath);
                CoordinateData data = new CoordinateData(new HashMap<>());
                gson.toJson(data, writer);
                writer.close();
            }
            reader = Files.newBufferedReader(Paths.get(dataPath));
        } catch (IOException ignored) {}
        return gson.fromJson(reader, CoordinateData.class);
    }

    @Override
    public void onEnable() {
        dataFolderPath = getDataFolder().getPath();
        dataPath = dataFolderPath + "/TeleportData.json";
        getCommand("listlocations").setExecutor(new ListLocations(this));
        getCommand("removelocation").setExecutor(new RemoveLocation(this));
        getServer().getPluginManager().registerEvents(new AnvilPrepareListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new EntityBurn(), this);
        getServer().getConsoleSender().sendMessage("Advanced Chorus Fruit plugin ready");
    }

    @Override
    public void onDisable() {
    }
}
