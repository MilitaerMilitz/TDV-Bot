package com.github.militalex.music;

import com.github.militalex.music.loadresult.AudioLoadStandardResult;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    //Singleton Pattern
    private static PlayerManager INSTANCE;

    public static PlayerManager getInstance() {
        if (INSTANCE == null){
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    private final Map<Long, GuildMusicManager> musicManagers = new ConcurrentHashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    public PlayerManager(){
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild){
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildID -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }


    public void loadAndPlay(TextChannel channel, String url){
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadStandardResult(musicManager.getScheduler(), channel));
    }

    public void loadAndPlay(TextChannel channel, String url, AudioLoadResultHandler resultHandler){
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, url, resultHandler);
    }
}
