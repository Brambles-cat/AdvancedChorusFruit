package me.jiovannyalejos.advancedchorusfruit.listeners;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;

import java.io.*;

public class EntityBurn implements Listener {
    @EventHandler
    public void onItemBurn(EntityCombustByBlockEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Item) {
            Item item = (Item) entity;
            String itemDisplayName = item.getItemStack().getItemMeta().getDisplayName();
            if(item.getItemStack().getType() == Material.ENDER_EYE && itemDisplayName.startsWith("set ") && itemDisplayName.length() > 4) {
                Gson gson = new Gson();
                CoordinateData data = AdvancedChorusFruit.getData();
                Location entityLoc = entity.getLocation();
                String displayName = itemDisplayName.substring(4);
                if(data.locNames.contains(displayName)) {
                    data.coordinates.set(data.locNames.indexOf(displayName), Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + Math.floor(entityLoc.getZ()) + .5);
                    Bukkit.broadcastMessage("Changed warp location of \"" + itemDisplayName.substring(4) + "\" to X:" + item.getLocation().getBlockX() + " Y:" + item.getLocation().getBlockY() + " Z:" + item.getLocation().getBlockZ());
                } else {
                    data.coordinates.add(Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
                    data.locNames.add(displayName);
                    Bukkit.broadcastMessage("New warp location \"" + itemDisplayName.substring(4) + "\" set at X:" + item.getLocation().getBlockX() + " Y:" + item.getLocation().getBlockY() + " Z:" + item.getLocation().getBlockZ());
                }
                try {
                    Writer writer = new FileWriter(AdvancedChorusFruit.dataPath);
                    gson.toJson(data, writer);
                    writer.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
}
