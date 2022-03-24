package com.github.militalex.main;

import com.github.militalex.command.api.CommandListener;
import com.github.militalex.command.api.CommandRegistry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

/**
 * @author Alexander Ley
 * @version 1.0
 */
public final class TdvBot {

    private final JDA manager;

    // Singleton Pattern
    private static TdvBot INSTANCE;

    public static TdvBot getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new TdvBot();
            }
            catch (LoginException e) {
                throw new IllegalStateException("TdvBot cannot be Initialized.", e);
            }
        }
        return INSTANCE;
    }

    private TdvBot() throws LoginException {
        CommandRegistry.getInstance().registerCommands();

        JDABuilder builder = JDABuilder.createDefault("OTUzNzA5NjQ1NDkzNTk2MjQw.YjIhUg.nGKWWIMXwO7a2O5FK8pu_aQ1tWQ",
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_VOICE_STATES);

        builder.enableCache(CacheFlag.VOICE_STATE);
        builder.addEventListeners(new CommandListener());

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.listening("Linphators Musik"));

        manager = builder.build();
    }

    public JDA getManager() {
        return manager;
    }

    public void shutdown(){
        manager.shutdown();
    }
}
