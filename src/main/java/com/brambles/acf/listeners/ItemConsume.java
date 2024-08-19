package com.brambles.acf.listeners;

import com.brambles.acf.PluginData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.brambles.acf.AdvancedChorusFruit.PluginPermission;

public class ItemConsume implements Listener {
    public static Map<UUID, String> consumers = new HashMap<>();

    @EventHandler
    public void onItemConsumed(PlayerItemConsumeEvent event) {
        ItemStack consumedItem = event.getItem();

        ItemMeta meta;
        if (consumedItem.getType() != Material.CHORUS_FRUIT || (meta = consumedItem.getItemMeta()) == null || !meta.hasLore() || !meta.getLore().contains("warp"))
            return;

        Player player = event.getPlayer();

        if (PluginData.playerHasPermission(player, PluginPermission.USE_WARPS))
            consumers.put(event.getPlayer().getUniqueId(), meta.getDisplayName());
        else
            player.sendMessage("Missing permission");
    }
}
