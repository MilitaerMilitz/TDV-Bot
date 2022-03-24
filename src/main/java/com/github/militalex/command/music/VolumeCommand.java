package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandPattern;
import com.github.militalex.command.api.CommandUnit;
import com.github.militalex.music.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class VolumeCommand extends Command {

    public VolumeCommand() {
        super("volume", List.of(Permission.MESSAGE_SEND), createCommandPattern(), "Stellt die Lautstärke ein");
    }

    private static CommandPattern createCommandPattern(){
        final CommandPattern pattern = new CommandPattern();
        pattern.getHead().add(new CommandPattern.CommandNode(CommandUnit.PERCENT));
        return pattern;
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        if (verifyMemberForMusicCommand(member, self, channel)) {
            int volume = Integer.parseInt(getArgs(message).get(0));
            PlayerManager.getInstance().getMusicManager(channel.getGuild()).getScheduler().setVolume(volume);
        }
    }
}
