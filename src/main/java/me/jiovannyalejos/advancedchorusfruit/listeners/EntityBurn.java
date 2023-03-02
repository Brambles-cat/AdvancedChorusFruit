package me.jiovannyalejos.advancedchorusfruit.listeners;

import java.util.Map;

import me.jiovannyalejos.advancedchorusfruit.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityBurn implements Listener {
    @EventHandler
    public void onItemBurn(EntityCombustByBlockEvent event) {
        Entity eye = event.getEntity();
        if (eye instanceof Item) {
            Item item = (Item) eye;
            ItemMeta meta = item.getItemStack().getItemMeta();
            if (item.getItemStack().getType() == Material.ENDER_EYE && meta.getLore() != null && meta.getLore().contains("set")) {
                boolean permitted = false;
                for(Entity e : eye.getNearbyEntities(4.0, 4.0, 4.0)) {
                    if(!(e instanceof Player)) continue;
                    if (!e.isOp() && PluginData.getPermissions(e.getUniqueId()).stream().noneMatch(p -> p.equals("all") || p.equals("set_warps"))) {
                        e.sendMessage("Missing permissions");
                    } else permitted = true;
                }
                if(!permitted) return;
                PluginData data = PluginData.getData();
                Environment env = event.getEntity().getWorld().getEnvironment();
                Map<String, String> locations = data.dimensions.get(env);
                Location entityLoc = eye.getLocation();
                String displayName = meta.getDisplayName();
                if (locations.containsKey(displayName)) {
                    locations.replace(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
                    Bukkit.broadcastMessage(
                            "Changed warp location of \""
                                    + displayName
                                    + "\" to X:"
                                    + item.getLocation().getBlockX()
                                    + " Y:"
                                    + item.getLocation().getBlockY()
                                    + " Z:"
                                    + item.getLocation().getBlockZ()
                    );
                } else {
                    locations.put(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
                    Bukkit.broadcastMessage(
                            "New warp location \""
                                    + displayName
                                    + "\" set at X:"
                                    + item.getLocation().getBlockX()
                                    + " Y:"
                                    + item.getLocation().getBlockY()
                                    + " Z:"
                                    + item.getLocation().getBlockZ()
                    );
                }
                data.dimensions.replace(env, locations);
                PluginData.writeData(data);
            }
        }
    }
}
