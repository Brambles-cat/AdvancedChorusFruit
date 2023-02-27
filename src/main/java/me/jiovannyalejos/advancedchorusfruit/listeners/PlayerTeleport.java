package me.jiovannyalejos.advancedchorusfruit.listeners;

import java.util.Map;
import java.util.regex.Pattern;
import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerTeleport implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == TeleportCause.CHORUS_FRUIT) {
            Player player = event.getPlayer();
            ItemMeta meta = player.getItemInUse().getItemMeta();
            if (meta.getLore() != null && meta.getLore().contains("warp")) {
                Map<String, String> data = AdvancedChorusFruit.getData().dimensions.get(player.getWorld().getEnvironment());
                if (data.containsKey(meta.getDisplayName())) {
                    String[] coords = data.get(meta.getDisplayName()).split(Pattern.quote("|"));
                    event.setTo(new Location(player.getWorld(), Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
                    event.getTo().setDirection(player.getLocation().getDirection());
                }
            }
        }
    }
}