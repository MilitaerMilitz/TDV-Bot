package com.github.militalex.music.loadresult;

import com.github.militalex.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.TextChannel;

public class AudioLoadStandardResult implements AudioLoadResultHandler {

    private final TrackScheduler scheduler;
    private final TextChannel channel;

    public AudioLoadStandardResult(TrackScheduler scheduler, TextChannel channel){
        this.scheduler = scheduler;
        this.channel = channel;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        scheduler.queue(track);
        channel.sendMessage("Track geladen.").queue();
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        playlist.getTracks().forEach(track -> scheduler.queue(track));
        channel.sendMessage("Playlist geladen.").queue();
    }

    @Override
    public void noMatches() {
        if (scheduler.isEmpty()) scheduler.closeConnection();
        channel.sendMessage("Konnte Inhalt nicht finden.").queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        if (scheduler.isEmpty()) scheduler.closeConnection();
        channel.sendMessage("Laden fehlgeschlagen.").queue();
    }
}
