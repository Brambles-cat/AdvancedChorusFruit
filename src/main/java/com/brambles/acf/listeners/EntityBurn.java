package com.brambles.acf.listeners;

import java.util.Map;

import com.brambles.acf.PluginData;
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
import org.bukkit.persistence.PersistentDataType;

import static com.brambles.acf.AdvancedChorusFruit.PluginPermission;

public class EntityBurn implements Listener {
    @EventHandler
    public void onItemBurn(EntityCombustByBlockEvent event) {
        Entity burnedEntity = event.getEntity();

        if (burnedEntity instanceof Item item) {
            if (item.getItemStack().getType() != Material.ENDER_EYE || !item.getItemStack().hasItemMeta())
                return;

            ItemMeta meta = item.getItemStack().getItemMeta();
            Player dropper = Bukkit.getPlayer(meta.getPersistentDataContainer().get(ItemDropped.droppedDataKey, PersistentDataType.STRING));

            if (dropper == null) return;

            if (!PluginData.playerHasPermission(dropper, PluginPermission.SET_WARPS)) {
                dropper.sendMessage("Couldn't set warp point; Missing Permissions");
                return;
            }

            Environment env = event.getEntity().getWorld().getEnvironment();
            Map<String, String> warpPoints = PluginData.getWarpPoints(env);
            Location entityLoc = burnedEntity.getLocation();
            String displayName = meta.getDisplayName();

            if (warpPoints.containsKey(displayName)) {
                warpPoints.replace(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
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
                warpPoints.put(displayName, Math.floor(entityLoc.getX()) + 0.5 + "|" + Math.floor(entityLoc.getY()) + "|" + (Math.floor(entityLoc.getZ()) + 0.5));
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
        }
    }
}
