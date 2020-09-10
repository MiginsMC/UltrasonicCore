package me.mig.ultrasoniccore;

import me.mig.ultrasoniccore.commands.Reload;
import me.mig.ultrasoniccore.commands.Suicide;
import me.mig.ultrasoniccore.commands.Teleport;
import me.mig.ultrasoniccore.events.SleepEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.Collection;

public class UltrasonicCore extends JavaPlugin {

    public static JDA jda;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getCommand("reload").setExecutor(new Reload(this));
        getCommand("suicide").setExecutor(new Suicide());
        getCommand("teleport").setExecutor(new Teleport());
        getServer().getPluginManager().registerEvents(new SleepEvent(), this);

        if (getBot()) {

            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                StringBuilder online = new StringBuilder();
                final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player player : players) {

                    if (online.length() > 0) {
                        online.append(", ");
                    }
                    online.append(player.getDisplayName());
                }

                TextChannel tc = jda.getTextChannelById("748466123548393475");
                assert tc != null;
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    tc.sendMessage("There are " + Bukkit.getOnlinePlayers().size() + " players online right now. \n"
                            + online.toString()).queue();
                } else {
                    tc.sendMessage("There are no players online right now ;(").queue();
                }
            }, 200L, 200L);
        } else {
            System.out.println("Bot didn't start :(");
        }
    }

    @Override
    public void onDisable() {

    }

    private boolean getBot() {
        try {
            String token = this.getConfig().getString("token");
            if (token != null) {
                jda = JDABuilder.createDefault(token)
                        .setActivity(Activity.watching("my children"))
                        //.enableIntents(GatewayIntent.GUILD_MEMBERS)
                        .build();
                System.out.println("Bot has started :)");
                return true;
            } else {
                System.out.println("Token not entered in config file");
                return false;
            }
            } catch (LoginException e){
                e.printStackTrace();
                return false;
            }
    }
}
