package me.JiovannyAlejos.advancedChorusFruit.listeners;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;

import com.google.gson.Gson;

import me.JiovannyAlejos.advancedChorusFruit.CoordinateData;



public class entityBurn implements Listener {
	@EventHandler
	public void onItemBurn(EntityCombustByBlockEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Item) {
			Item item = (Item) entity;
			String itemDisplayName = item.getItemStack().getItemMeta().getDisplayName();
			if(item.getItemStack().getType() == Material.ENDER_EYE && itemDisplayName.substring(0, 4).equals("set ") && itemDisplayName.length() > 4) {
				Gson gson = new Gson();
				System.out.println(1);
				try {
					Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\gaela\\OneDrive\\Documents\\advanedChorusFruitPlugin\\src\\me\\JiovannyAlejos\\advancedChorusFruit\\tpData.json"));
					CoordinateData data = gson.fromJson(reader, CoordinateData.class);
					Location entityLoc = entity.getLocation();
					data.coordinates.add(String.valueOf(Math.floor(entityLoc.getX()) + 0.5) + "|" + String.valueOf(Math.floor(entityLoc.getY())) + "|" + String.valueOf(Math.floor(entityLoc.getZ()) + .5));
					data.locNames.add(itemDisplayName.substring(4));
					System.out.println(2);
					Writer writer = new FileWriter("C:\\Users\\gaela\\OneDrive\\Documents\\advanedChorusFruitPlugin\\src\\me\\JiovannyAlejos\\advancedChorusFruit\\tpData.json");
					System.out.println(itemDisplayName.substring(4));
					Bukkit.broadcastMessage("New warp location \"" + itemDisplayName.substring(4) + "\" set at X:" + item.getLocation().getBlockX() + " Y:" + item.getLocation().getBlockY() + " Z:" + item.getLocation().getBlockZ());
					gson.toJson(data, writer);
					writer.close();
					reader.close();
				} catch (IOException e) {e.printStackTrace(); System.out.println("poopy burn");}

			}
		}
	}
}
