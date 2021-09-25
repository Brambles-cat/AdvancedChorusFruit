package me.jiovannyalejos.advancedchorusfruit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilPrepareListener implements Listener {
    @EventHandler
    public void GUIInteracted(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();
        if(inv.getItem(1) == null && inv.getItem(0) != null && inv.getItem(0).getItemMeta().getLore() == null) {
            List<String> lore = new ArrayList<>();
            ItemMeta meta = event.getResult().getItemMeta();
            if(inv.getItem(0).getType() == Material.CHORUS_FRUIT && inv.getRenameText().startsWith("warp ") && inv.getRenameText().length() > 5) {
                lore.add("warp");
                meta.setDisplayName(inv.getRenameText().substring(5));
            } else if(inv.getItem(0).getType() == Material.ENDER_EYE && inv.getRenameText().startsWith("set ") && inv.getRenameText().length() > 4) {
                lore.add("set");
                meta.setDisplayName(inv.getRenameText().substring(4));
            } else return;
            meta.setLore(lore);
            event.getResult().setItemMeta(meta);
        }
    }
}