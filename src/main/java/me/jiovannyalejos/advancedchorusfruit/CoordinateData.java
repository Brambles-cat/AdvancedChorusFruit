package me.jiovannyalejos.advancedchorusfruit;

import org.bukkit.World;

import java.util.ArrayList;

public class CoordinateData {
    public Dimension nether;
    public Dimension end;
    public Dimension overworld;
    public CoordinateData(ArrayList<String> coordinates, ArrayList<String> locNames) {
        this.nether = new Dimension(coordinates, locNames);
        this.overworld = new Dimension(coordinates, locNames);
        this.end = new Dimension(coordinates, locNames);
    }
    public static Dimension getDimData(World.Environment env, CoordinateData data) {
         if(env == World.Environment.NORMAL) {
            return data.overworld;
        } else if(env == World.Environment.NETHER) {
            return data.nether;
        } else {
            return data.end;
        }
    }
    // Note: as I wrote this today i've had some weird mental stuff going on so this probably isn't as efficient as I could have made it ;-;
    public static CoordinateData assignData(World.Environment env, CoordinateData data, Dimension dimData) {
        if(env == World.Environment.NORMAL) {
            data.overworld = dimData;
        } else if(env == World.Environment.NETHER) {
            data.nether = dimData;
        } else {
            data.end = dimData;
        }
        return data;
    }
}
