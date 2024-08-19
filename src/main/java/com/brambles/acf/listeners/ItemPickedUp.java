package com.brambles.acf.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemPickedUp implements Listener {

    @EventHandler
    public void onItemPickedUp(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        ItemMeta meta;
        if (itemStack.getType() != Material.ENDER_EYE || (meta = itemStack.getItemMeta()) == null)
            return;

        meta.getPersistentDataContainer().remove(ItemDropped.droppedDataKey);
        itemStack.setItemMeta(meta);
    }
}
