package me.jiovannyalejos.advancedchorusfruit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import org.bukkit.World.Environment;

public class PluginData {
    public static Map<UUID, List<String>> playerPermissions;
    public static String dataPath, dataFolderPath;
    static Gson gson = new Gson();
    static Reader reader;

    public Map<Environment, Map<String, String>> dimensions;
    public Map<UUID, List<String>> permissions;

    public PluginData() {
        dimensions = new HashMap<>();
        dimensions.put(Environment.NORMAL, new HashMap<>());
        dimensions.put(Environment.NETHER, new HashMap<>());
        dimensions.put(Environment.THE_END, new HashMap<>());
        permissions = new HashMap<>();
    }

    public static String format(Environment environment) {
        switch(environment) {
            case NORMAL:
                return "§2Overworld§f";
            case NETHER:
                return "§cNether§f";
            default:
                return "§eEnd§f";
        }
    }

    public static PluginData getData() {
        try {
            if (!Files.exists(Paths.get(dataPath))) {
                Files.createDirectory(Paths.get(dataFolderPath));
                Files.createFile(Paths.get(dataPath));
                Writer writer = new FileWriter(dataPath);
                PluginData data = new PluginData();
                gson.toJson(data, writer);
                writer.close();
            }
            reader = Files.newBufferedReader(Paths.get(dataPath));
        } catch (IOException exception) {exception.printStackTrace();}
        PluginData data = gson.fromJson(reader, PluginData.class);
        return data;
    }

    public static void writeData(PluginData data) {
        try {
            Writer writer = new FileWriter(dataPath);
            gson.toJson(data, writer);
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public static List<String> getPermissions(UUID playerId) {
        if (playerPermissions.get(playerId) == null) {
            playerPermissions.put(playerId, new ArrayList<>());
            PluginData updated = getData();
            updated.permissions.put(playerId, new ArrayList<>());
            writeData(updated);
        }
        return playerPermissions.get(playerId);
    }
}
