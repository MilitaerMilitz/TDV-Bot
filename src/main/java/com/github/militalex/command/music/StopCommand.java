package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.EmptyPattern;
import com.github.militalex.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop", List.of(Permission.MESSAGE_SEND), new EmptyPattern(), "Stoppt die Musik und lässt den Bot verschwinden.");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        if (verifyMemberForMusicCommand(member, self, channel)) {
            PlayerManager.getInstance().getMusicManager(channel.getGuild()).getScheduler().cancelAll();
        }
    }
}
