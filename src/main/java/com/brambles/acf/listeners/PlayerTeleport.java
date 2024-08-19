package com.brambles.acf.listeners;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import com.brambles.acf.PluginData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerTeleport implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == TeleportCause.CHORUS_FRUIT) {
            Player player = event.getPlayer();
            UUID playerId = player.getUniqueId();

            if (!ItemConsume.consumers.containsKey(playerId)) return;

            Map<String, String> warpPoints = PluginData.getWarpPoints(player.getWorld().getEnvironment());
            String destinationName = ItemConsume.consumers.get(playerId);

            if (!warpPoints.containsKey(destinationName)) return;

            String[] destinationCoords = warpPoints.get(destinationName).split(Pattern.quote("|"));

            event.setTo(new Location(player.getWorld(), Double.parseDouble(destinationCoords[0]), Double.parseDouble(destinationCoords[1]), Double.parseDouble(destinationCoords[2])));
            event.getTo().setDirection(player.getLocation().getDirection());

            ItemConsume.consumers.remove(playerId);
        }
    }
}