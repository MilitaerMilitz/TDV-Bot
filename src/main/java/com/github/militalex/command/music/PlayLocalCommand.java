package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandPattern;
import com.github.militalex.command.api.CommandRegistry;
import com.github.militalex.command.api.CommandUnit;
import com.github.militalex.music.PlayerManager;
import com.github.militalex.music.TrackScheduler;
import com.github.militalex.music.loadresult.AudioLoadStandardResult;
import com.github.militalex.music.loadresult.AudioLoadYoutubeResult;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayLocalCommand extends Command {

    public PlayLocalCommand() {
        super("play", List.of(Permission.MESSAGE_SEND), createCommandPattern(), "Spielt Linphators Musik.");
    }

    private static CommandPattern createCommandPattern(){
        final CommandPattern pattern = new CommandPattern();
        pattern.getHead().add(new CommandPattern.CommandNode(CommandUnit.STRING));
        return pattern;
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!memberVoiceState.inAudioChannel()){
            channel.sendMessage("Dieser Command ist nur für Leute im Sprachkanal.").queue();
            return;
        }

        if (selfVoiceState.inAudioChannel() && !memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("Der Bot ist schon in einem Sprachkanal.").queue();
            return;
        }

        final TrackScheduler scheduler = PlayerManager.getInstance().getMusicManager(channel.getGuild()).getScheduler();

        if (!selfVoiceState.inAudioChannel()){
            final AudioManager audioManager = self.getGuild().getAudioManager();
            final AudioChannel memberChannel = memberVoiceState.getChannel();
            audioManager.openAudioConnection(memberChannel);

            scheduler.setAudioManager(audioManager);
        }

        final String msg = message.getContentDisplay().replace(CommandRegistry.CMD_PREFIX, "").replace(getPrefix(), "").trim().toLowerCase();

        final Path musicPath = Path.of("assets/music");
        final List<String> musicList = Arrays.stream(Objects.requireNonNull(musicPath.toFile().list()))
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        final List<String> matches = musicList.stream().filter(s -> s.contains(msg)).collect(Collectors.toList());

        if (matches.isEmpty()){
            channel.sendMessage("Den Song hat Linphator noch nicht gebaut. Mit \"" +
                    CommandRegistry.CMD_PREFIX + "music\" kannst du dir eine Liste verfügbarer Stücke anzeigen lassen.").queue();

            if (scheduler.isEmpty()) scheduler.closeConnection();

            return;
        }

        final String url = Path.of("assets/music/" + matches.get(0)).toAbsolutePath().toString();

        scheduler.setVolume(25);

        PlayerManager.getInstance().loadAndPlay(channel, url, new AudioLoadStandardResult(scheduler, channel));
    }
}
