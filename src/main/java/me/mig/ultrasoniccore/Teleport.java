package me.mig.ultrasoniccore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player tplayer = player.getServer().getPlayer(args[0]);
                if (tplayer != null) {
                    player.teleport(tplayer);
                    player.sendMessage(ChatColor.GREEN + "Teleported to " + tplayer.getName() + " ;)");
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "That player is not online ;(");
                }
            } else if (args.length == 2) {
                Player player1 = player.getServer().getPlayer(args[0]);
                Player player2 = player.getServer().getPlayer(args[1]);
                if (player1 != null && player2 != null) {
                    player1.teleport(player2);
                    player.sendMessage(ChatColor.GREEN + "Teleporting " + player1.getName() + " to " + player2.getName() + " ;)");
                } else {
                    player.sendMessage(ChatColor.RED + "Either one or both of those players are not online ;(");
                }
                return true;
            } else if (args.length > 2) {
                    try {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);
                        Location location = new Location(player.getWorld(), x, y, z);
                        if (args.length == 5) {
                            location.setYaw(Float.parseFloat(args[3]));
                            location.setPitch(Float.parseFloat(args[4]));
                        }
                        player.teleport(location);
                        player.sendMessage(ChatColor.GREEN + "Teleported ;)");
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "This is not a valid location ;(");
                    }
                    return true;
                }

        }
        return false;
    }

}
