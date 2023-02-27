package me.jiovannyalejos.advancedchorusfruit;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import me.jiovannyalejos.advancedchorusfruit.commands.ListLocations;
import me.jiovannyalejos.advancedchorusfruit.commands.RemoveLocation;
import me.jiovannyalejos.advancedchorusfruit.commands.SetOpExclusive;
import me.jiovannyalejos.advancedchorusfruit.listeners.AnvilPrepareListener;
import me.jiovannyalejos.advancedchorusfruit.listeners.EntityBurn;
import me.jiovannyalejos.advancedchorusfruit.listeners.PlayerTeleport;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedChorusFruit extends JavaPlugin {
    public static String dataPath;
    static String dataFolderPath;
    static Gson gson = new Gson();
    static Reader reader;

    public static CoordinateData getData() {
        try {
            if (!Files.exists(Paths.get(dataPath))) {
                Files.createDirectory(Paths.get(dataFolderPath));
                Files.createFile(Paths.get(dataPath));
                Writer writer = new FileWriter(dataPath);
                CoordinateData data = new CoordinateData(new HashMap<>(), false);
                gson.toJson(data, writer);
                writer.close();
            }

            reader = Files.newBufferedReader(Paths.get(dataPath));
        } catch (IOException var2) {
        }

        CoordinateData data = gson.fromJson(reader, CoordinateData.class);
        return data;
    }

    public static void writeData(Environment env, Map<String, String> data, CoordinateData original) {
        original.dimensions.replace(env, data);

        try {
            Writer writer = new FileWriter(dataPath);
            gson.toJson(original, writer);
            writer.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    public static void writeData(CoordinateData data) {
        try {
            Writer writer = new FileWriter(dataPath);
            gson.toJson(data, writer);
            writer.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void onEnable() {
        dataFolderPath = this.getDataFolder().getPath();
        dataPath = dataFolderPath + "/TeleportData.json";
        this.getCommand("listlocations").setExecutor(new ListLocations(this));
        this.getCommand("setopexclusive").setExecutor(new SetOpExclusive(this));
        this.getCommand("removelocation").setExecutor(new RemoveLocation(this));
        this.getServer().getPluginManager().registerEvents(new AnvilPrepareListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        this.getServer().getPluginManager().registerEvents(new EntityBurn(), this);
        this.getServer().getConsoleSender().sendMessage("Advanced Chorus Fruit plugin ready");
    }

    public void onDisable() {
    }
}
