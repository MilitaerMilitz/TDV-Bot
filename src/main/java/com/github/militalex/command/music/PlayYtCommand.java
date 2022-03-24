package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandPattern;
import com.github.militalex.command.api.CommandPattern.CommandNode;
import com.github.militalex.command.api.CommandRegistry;
import com.github.militalex.command.api.CommandUnit;
import com.github.militalex.music.PlayerManager;
import com.github.militalex.music.TrackScheduler;
import com.github.militalex.music.loadresult.AudioLoadYoutubeResult;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class PlayYtCommand extends Command {
    public PlayYtCommand() {
        super("ytplay", List.of(Permission.MESSAGE_SEND), PlayYtCommand.createCommandPattern(),"Spielt Musik von YouTube.");
    }

    private static CommandPattern createCommandPattern(){
        final CommandPattern pattern = new CommandPattern();
        pattern.getHead().add(new CommandNode(CommandUnit.STRING));
        return pattern;
    }

    @SuppressWarnings("ConstantConditions")
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

        String url = getArgs(message).get(0);

        if (url.startsWith("http")){
            PlayerManager.getInstance().loadAndPlay(channel, url);
            return;
        }

        url = "ytsearch: " + message.getContentDisplay().replace(CommandRegistry.CMD_PREFIX, "").replace(getPrefix(), "");
        //url = Path.of("assets/music/Bows 1.mp3").toAbsolutePath().toString();

        PlayerManager.getInstance().loadAndPlay(channel, url, new AudioLoadYoutubeResult(scheduler, channel));
    }
}
