package me.JiovannyAlejos.advancedChorusFruit.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if(event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
			if(player.getItemInUse().getItemMeta().getDisplayName().substring(0, 4).equals("warp")) {
				//event.setCancelled(true);
				event.setTo(new Location(player.getWorld(), player.getLocation().getX() + 5, 80.00, player.getLocation().getZ() + 5));
				//Bukkit.getScheduler().runTaskLater((Plugin) this, () -> {
				//	player.teleport(new Location(player.getWorld(), player.getLocation().getX() + 5, 80.00, player.getLocation().getZ() + 5));
				//}, 1L);
			}
		}
	}
}
