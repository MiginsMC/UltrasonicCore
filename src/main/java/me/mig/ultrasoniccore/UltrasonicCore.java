package me.mig.ultrasoniccore;

import me.mig.ultrasoniccore.commands.Reload;
import me.mig.ultrasoniccore.commands.Suicide;
import me.mig.ultrasoniccore.commands.Teleport;
import me.mig.ultrasoniccore.events.SleepEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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
                String channelID = getConfig().getString("channel");
                if (channelID == null) {
                    System.out.print("Channel id not present :(");
                } else {
                    TextChannel tc = jda.getTextChannelById(channelID);
                    // does this even work?!
                    assert tc != null;

                    Consumer<List<Message>> callback = (response) -> {
                        Message contains = null;
                        for (Message m : response) {
                            if (m.getEmbeds().size() > 0) {
                                contains = m;
                                break;
                            }
                        }
                        if (contains != null) {
                            contains.editMessage(getEmbed(online)).queue();
                        } else {
                            tc.sendMessage(getEmbed(online)).queue();
                        }

                    };
                    // Executes callback, above happens after the messages have been retrieved.
                    tc.getHistory().retrievePast(30).queue(callback);
                }
            }, 100L, 200L);

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

    public MessageEmbed getEmbed(StringBuilder online) {
        EmbedBuilder eb = new EmbedBuilder();
        int players = Bukkit.getOnlinePlayers().size();

        double[] tps = Bukkit.getTPS();
        List<String> rounded = new ArrayList<>();
        for (double x : tps) {
            double r = round(x, 2);
            rounded.add(Double.toString(r));
        }
//        System.out.println("Rounded TPS values: " + Arrays.toString(rounded));

        eb.setTitle("Server Stats")
        .setColor(Color.CYAN)
        .addField("Players Online (" + players + ")", players > 0 ? online.toString() : "No ones here :(", true)

        .addField("TPS from last 1m, 5m, 15m", String.join(" ", rounded), true)
        .setFooter("caryl sees all", jda.getSelfUser().getAvatarUrl())
        .setTimestamp(Instant.now());
       return eb.build();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
