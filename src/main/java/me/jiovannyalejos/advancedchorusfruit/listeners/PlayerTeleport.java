package me.jiovannyalejos.advancedchorusfruit.listeners;

import me.jiovannyalejos.advancedchorusfruit.AdvancedChorusFruit;
import me.jiovannyalejos.advancedchorusfruit.CoordinateData;
import me.jiovannyalejos.advancedchorusfruit.Dimension;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.regex.Pattern;

public class PlayerTeleport implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if(!(event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)) return;
        Player player = event.getPlayer();
        String itemDisplayName = player.getItemInUse().getItemMeta().getDisplayName();
        if(itemDisplayName.startsWith("warp ")) {
            CoordinateData data = AdvancedChorusFruit.getData();
            Dimension dimData = CoordinateData.getDimData(event.getPlayer().getWorld().getEnvironment(), data);
            if(dimData.locNames.contains(itemDisplayName.substring(5))) {
                String[] coords = dimData.coordinates.get(dimData.locNames.indexOf(itemDisplayName.substring(5))).split(Pattern.quote("|"));
                event.setTo(new Location(player.getWorld(), Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2])));
            }
        }
    }
}
