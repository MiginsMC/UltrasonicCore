package me.mig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Reload implements CommandExecutor {

    private JavaPlugin plugin;

    public Reload(JavaPlugin pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player != null) {
                player.sendMessage("Reloaded");
            }

        }
        return true;
    }

}
