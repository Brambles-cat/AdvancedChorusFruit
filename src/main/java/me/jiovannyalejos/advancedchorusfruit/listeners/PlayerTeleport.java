package me.jiovannyalejos.advancedchorusfruit.listeners;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
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
            if (ItemConsume.consumers.containsKey(playerId)) {
                Map<String, String> data = AdvancedChorusFruit.getData().dimensions.get(player.getWorld().getEnvironment());
                String warpLocation = ItemConsume.consumers.get(playerId);
                if (data.containsKey(warpLocation)) {
                    String[] coords = data.get(warpLocation).split(Pattern.quote("|"));
                    event.setTo(new Location(player.getWorld(), Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
                    event.getTo().setDirection(player.getLocation().getDirection());
                    ItemConsume.consumers.remove(playerId);
                }
            }
        }
    }
}