package com.brambles.acf.listeners;

import com.brambles.acf.AdvancedChorusFruit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemDropped implements Listener {
    static NamespacedKey droppedDataKey;

    public ItemDropped(AdvancedChorusFruit plugin) {
        droppedDataKey = new NamespacedKey(plugin, "dropped_by");
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();

        ItemMeta meta;
        if (itemStack.getType() != Material.ENDER_EYE || !itemStack.hasItemMeta() || !(meta = itemStack.getItemMeta()).hasLore() || !meta.getLore().contains("set"))
            return;

        meta.getPersistentDataContainer().set(droppedDataKey, PersistentDataType.STRING, event.getPlayer().getName());
        itemStack.setItemMeta(meta);
    }
}
