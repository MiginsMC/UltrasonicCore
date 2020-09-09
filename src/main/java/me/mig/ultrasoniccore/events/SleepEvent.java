package me.mig.ultrasoniccore.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SleepEvent implements Listener {

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            player.getWorld().setTime(1000);
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Good morning frens, " + player.getName() + " slept");
        }
    }
}
