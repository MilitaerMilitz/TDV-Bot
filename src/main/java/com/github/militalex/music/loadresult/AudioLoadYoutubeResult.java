package com.github.militalex.music.loadresult;

import com.github.militalex.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import net.dv8tion.jda.api.entities.TextChannel;

public class AudioLoadYoutubeResult extends AudioLoadStandardResult{

    public AudioLoadYoutubeResult(TrackScheduler scheduler, TextChannel channel) {
        super(scheduler, channel);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        super.trackLoaded(playlist.getTracks().get(0));
    }
}
