package me.jiovannyalejos.advancedchorusfruit.listeners;

import com.google.gson.Gson;
import me.jiovannyalejos.advancedchorusfruit.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.Map;

public class EntityBurn implements Listener {
    @EventHandler
    public void onItemBurn(EntityCombustByBlockEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Item) {
            Item item = (Item) entity;
            ItemMeta meta = item.getItemStack().getItemMeta();
            if(item.getItemStack().getType() == Material.ENDER_EYE && meta.getLore() != null && meta.getLore().contains("set")) {
                World.Environment env = event.getEntity().getWorld().getEnvironment();
                Gson gson = new Gson();
                CoordinateData original = AdvancedChorusFruit.getData();
                Map<String, String> data = original.dimensions.get(env);
                Location entityLoc = entity.getLocation();
                String displayName = meta.getDisplayName();
                if(data.containsKey(displayName)) {
                    data.replace(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + Math.floor(entityLoc.getZ()) + .5);
                    Bukkit.broadcastMessage("Changed warp location of \"" + displayName + "\" to X:" + item.getLocation().getBlockX() + " Y:" + item.getLocation().getBlockY() + " Z:" + item.getLocation().getBlockZ());
                } else {
                    data.put(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
                    Bukkit.broadcastMessage("New warp location \"" + displayName + "\" set at X:" + item.getLocation().getBlockX() + " Y:" + item.getLocation().getBlockY() + " Z:" + item.getLocation().getBlockZ());
                }
                try {
                    Writer writer = new FileWriter(AdvancedChorusFruit.dataPath);
                    gson.toJson(CoordinateData.assignData(env, data, original), writer);
                    writer.close();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
}
