package me.jiovannyalejos.advancedchorusfruit;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World.Environment;

public class CoordinateData {
    public Map<Environment, Map<String, String>> dimensions;
    public boolean adminExclusive;

    public CoordinateData(Map<Environment, Map<String, String>> locData, boolean adminExclusive) {
        this.dimensions = locData;
        this.dimensions.put(Environment.NORMAL, new HashMap<>());
        this.dimensions.put(Environment.NETHER, new HashMap<>());
        this.dimensions.put(Environment.THE_END, new HashMap<>());
        this.adminExclusive = adminExclusive;
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
