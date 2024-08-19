package com.brambles.acf;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import static com.brambles.acf.AdvancedChorusFruit.PluginPermission;

public class PluginData {
    private static PluginData dataInstance = null;
    public static String dataPath, dataFolderPath;
    private static final Gson gson = new Gson();

    public Map<Environment, Map<String, String>> warpPoints;
    public Map<UUID, List<String>> playerPermissions;
    public List<String> defaultPermissions;

    private PluginData() {
        warpPoints = new HashMap<>();
        playerPermissions  = new HashMap<>();
        defaultPermissions = new ArrayList<>();

        warpPoints.put(Environment.NORMAL,  new HashMap<>());
        warpPoints.put(Environment.NETHER,  new HashMap<>());
        warpPoints.put(Environment.THE_END, new HashMap<>());

        for (PluginPermission permission : PluginPermission.values())
            defaultPermissions.add(permission.id());
    }

    public static String format(Environment environment) {
        return switch (environment) {
            case NORMAL -> "§2Overworld§f";
            case NETHER -> "§cNether§f";
            case THE_END -> "§eEnd§f";
            default -> ""; // Todo maybe
        };
    }

    public static void init() throws IOException {
        if (dataInstance != null) return;

        if (!Files.exists(Paths.get(dataPath))) {
            Files.createDirectory(Paths.get(dataFolderPath));
            Files.createFile(Paths.get(dataPath));
            dataInstance = new PluginData();
            return;
        }

        dataInstance = gson.fromJson(
                Files.newBufferedReader(Paths.get(dataPath)),
                PluginData.class
        );
    }

    public static void writeData() {
        try {
            Writer writer = new FileWriter(dataPath);
            gson.toJson(dataInstance, writer);
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<String> getPermissions(Player player) {
        if (player.isOp())
            return dataInstance.defaultPermissions.stream().map(
                    permission -> permission.startsWith("!") ? permission.substring(1) : permission
            ).toList();

        List<String> playerPermissions = dataInstance.playerPermissions.get(player.getUniqueId());

        if (playerPermissions == null)
            return new ArrayList<>(dataInstance.defaultPermissions);

        List<String> playerPermissionsCopy = new ArrayList<>(playerPermissions);

        dataInstance.defaultPermissions.forEach(permission -> {
            if (playerPermissions.stream().noneMatch(playerPermission -> playerPermission.endsWith(permission.substring(1))))
                playerPermissionsCopy.add(permission);
        });

        return playerPermissionsCopy;
    }

    public static boolean playerHasPermission(Player player, PluginPermission permission) {
        if (player.isOp()) return true;

        List<String> permissions = getPermissions(player);

        return permissions.contains(permission.id());
    }

    public static List<String> getPermissions() { return dataInstance.defaultPermissions; }

    public static void updatePermissions(UUID playerId, PluginPermission permission, boolean allowed) {
        List<String> currentPermissions = dataInstance.playerPermissions.get(playerId);
        String permissionString = allowed ? permission.id() : "!" + permission.id();

        if (currentPermissions == null) {
            currentPermissions = new ArrayList<>();
            currentPermissions.add(permissionString);
            dataInstance.playerPermissions.put(playerId, currentPermissions);
            return;
        }

        if (currentPermissions.contains(allowed ? permission.id() : "!" + permission.id())) return;

        currentPermissions.add(permission.id());
        currentPermissions.remove(allowed ? "!" + permission.id() : permission.id());
    }

    public static void resetPermissions(UUID playerId) {
        dataInstance.playerPermissions.remove(playerId);
    }

    public static void updateDefaultPermission(PluginPermission permission, boolean allowed) {
        String permissionString = allowed ? permission.id() : "!" + permission.id();

        if (dataInstance.defaultPermissions.contains(permissionString)) return;

        dataInstance.defaultPermissions.add(permissionString);
        dataInstance.defaultPermissions.remove(allowed ? "!" + permission.id() : permission.id());
    }

    public static Map<String, String> getWarpPoints(Environment env) {
        return dataInstance.warpPoints.get(env);
    }
}
