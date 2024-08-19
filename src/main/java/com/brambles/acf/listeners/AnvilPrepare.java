package com.brambles.acf.listeners;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;

public class AnvilPrepare implements Listener {
    @EventHandler
    public void GUIInteracted(PrepareAnvilEvent event) {
        AnvilView view = event.getView();

        if (event.getResult() == null)
            return;

        ItemStack itemStack = view.getItem(0);
        Material material;

        if (itemStack == null ||  ((material = itemStack.getType()) != Material.CHORUS_FRUIT && material != Material.ENDER_EYE) || itemStack.getItemMeta().getLore() != null)
            return;

        String lore;
        ItemMeta meta;

        try { meta = event.getResult().getItemMeta(); }
        catch (NullPointerException e) { e.printStackTrace(); return; }

        String renameText = view.getRenameText();

        if (material == Material.CHORUS_FRUIT) {
            if (!renameText.startsWith("warp ") || renameText.length() == 5)
                return;

            lore = "warp";
            meta.setDisplayName(renameText.substring(5));
        }
        else {
            if (!renameText.startsWith("set ") || renameText.length() == 4)
                return;

            lore = "set";
            meta.setDisplayName(renameText.substring(4));
        }

        meta.setLore(List.of(lore));
        event.getResult().setItemMeta(meta);
    }
}
