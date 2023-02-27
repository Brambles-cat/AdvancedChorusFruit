package me.jiovannyalejos.advancedchorusfruit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.World.Environment;

public class Data {
    public Map<Environment, Map<String, String>> dimensions;
    public Map<String, List<String>> permissions;
    public boolean adminExclusive = false;

    public Data() {
        dimensions = new HashMap<>();
        dimensions.put(Environment.NORMAL, new HashMap<>());
        dimensions.put(Environment.NETHER, new HashMap<>());
        dimensions.put(Environment.THE_END, new HashMap<>());
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
}
