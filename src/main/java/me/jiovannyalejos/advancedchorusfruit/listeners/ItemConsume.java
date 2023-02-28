package me.jiovannyalejos.advancedchorusfruit.listeners;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemConsume implements Listener {
    public static Map<UUID, String> consumers = new HashMap<>();

    @EventHandler
    public void onItemConsumed(PlayerItemConsumeEvent event) {
        ItemStack consumed = event.getItem();
        if (consumed.getType() != Material.CHORUS_FRUIT) return;
        ItemMeta meta = consumed.getItemMeta();
        if (meta.getLore() != null && meta.getLore().contains("warp")) {
            consumers.put(event.getPlayer().getUniqueId(), meta.getDisplayName());
        }
    }
}
