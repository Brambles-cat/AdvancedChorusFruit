package me.JiovannyAlejos.advancedChorusFruit.listeners;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.google.gson.Gson;

import me.JiovannyAlejos.advancedChorusFruit.CoordinateData;

public class PlayerConsumeItem implements Listener {
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		System.out.println(event.getItem().getType());
		String itemDisplayName = event.getItem().getItemMeta().getDisplayName();
		if(event.getItem().getType() == Material.GOLDEN_APPLE && itemDisplayName.substring(0, 5).equals("warp ")) {
			Gson gson = new Gson();
			try {
				Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\gaela\\OneDrive\\Documents\\advanedChorusFruitPlugin\\src\\me\\JiovannyAlejos\\advancedChorusFruit\\tpData.json"));
				CoordinateData data = gson.fromJson(reader, CoordinateData.class);
				System.out.println(data.locNames.contains(itemDisplayName.substring(5)));
				if(data.locNames.contains(itemDisplayName.substring(5))) {
					String[] coords = data.coordinates.get(data.locNames.indexOf(itemDisplayName.substring(5))).split("|");
					try {
						System.out.println(coords.toString());
					} catch(Error err) {
						System.out.println(coords + "hi");
					}
					player.teleport(new Location(player.getWorld(), Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
					reader.close();
				}
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}
