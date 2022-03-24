package com.github.militalex.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

public class TrackScheduler extends AudioEventAdapter {

    @Nullable
    private AudioManager audioManager;
    private final AudioPlayer player;
    private final ArrayDeque<AudioTrack> queue = new ArrayDeque<>();

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(@Nullable AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public void queue(AudioTrack track){
        if (!this.player.startTrack(track, true)){
            this.queue.offer(track);
        }
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void closeConnection(){
        if (audioManager != null) {
            audioManager.closeAudioConnection();
        }
    }

    public void setVolume(int volume){
        player.setVolume(volume);
    }

    public void nextTrack(){
        if (isEmpty()){
            closeConnection();
            return;
        }
        this.player.startTrack(queue.poll(), false);
    }

    public void cancelAll(){
        this.player.destroy();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext){
            nextTrack();
        }
    }
}
