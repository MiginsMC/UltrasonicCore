package me.mig;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Suicide implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
//            player.sendMessage("bye bye xx");
//            player.setMetadata("hi");
            player.setHealth(0);
            return true;
        }
        return false;
    }

//    @EventHandler
//    public void onDeath(PlayerDeathEvent event) {
//        if (event.getDeathMessage().equals())
//    }
}
