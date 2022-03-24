package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandPattern;
import com.github.militalex.command.api.EmptyPattern;
import com.github.militalex.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SkipCommand extends Command {

    public SkipCommand() {
        super("skip", List.of(Permission.MESSAGE_SEND), new EmptyPattern(), "Überspringt den aktuell laufenden Track.");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        if (verifyMemberForMusicCommand(member, self, channel)) {
            PlayerManager.getInstance().getMusicManager(channel.getGuild()).getScheduler().nextTrack();
        }
    }
}
