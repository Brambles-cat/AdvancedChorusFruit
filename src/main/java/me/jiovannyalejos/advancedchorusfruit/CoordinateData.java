package me.jiovannyalejos.advancedchorusfruit;

import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinateData {
    public Map<World.Environment, Map<String, String>> dimensions;
    public CoordinateData(Map<World.Environment, Map<String, String>> locData) {
        dimensions = locData;
        dimensions.put(World.Environment.NORMAL, new HashMap<>());
        dimensions.put(World.Environment.NETHER, new HashMap<>());
        dimensions.put(World.Environment.THE_END, new HashMap<>());
    }
    public static CoordinateData assignData(World.Environment env, Map<String, String> data, CoordinateData original) {
        original.dimensions.replace(env, data);
        return original;
    }
    public static String format(World.Environment environment) {
        switch (environment) {
            case NORMAL:
                return "§2Overworld§f";
            case NETHER:
                return "§cNether§f";
            default:
                return "§eEnd§f";
        }
    }
}
