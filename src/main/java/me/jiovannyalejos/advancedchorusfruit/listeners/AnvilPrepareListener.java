package me.jiovannyalejos.advancedchorusfruit.listeners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilPrepareListener implements Listener {
    @EventHandler
    public void GUIInteracted(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();
        if (inv.getItem(1) == null && inv.getItem(0) != null && inv.getItem(0).getItemMeta().getLore() == null) {
            List<String> lore = new ArrayList<>();
            ItemMeta meta = event.getResult().getItemMeta();
            if (inv.getItem(0).getType() == Material.CHORUS_FRUIT && inv.getRenameText().startsWith("warp ") && inv.getRenameText().length() > 5) {
                lore.add("warp");
                meta.setDisplayName(inv.getRenameText().substring(5));
            } else {
                if (inv.getItem(0).getType() != Material.ENDER_EYE || !inv.getRenameText().startsWith("set ") || inv.getRenameText().length() <= 4) {
                    return;
                }

                lore.add("set");
                meta.setDisplayName(inv.getRenameText().substring(4));
            }

            meta.setLore(lore);
            event.getResult().setItemMeta(meta);
        }
    }
}
