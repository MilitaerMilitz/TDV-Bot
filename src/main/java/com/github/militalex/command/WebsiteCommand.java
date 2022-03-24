package com.github.militalex.command;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandPattern;
import com.github.militalex.command.api.EmptyPattern;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class WebsiteCommand extends Command {

    public WebsiteCommand() {
        super("website", List.of(Permission.MESSAGE_SEND), new EmptyPattern(), "Zeigt dir den Link zu unserer Website.");
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        channel.sendMessage("https://tdv.minecraftmusical.com/").queue();
    }
}
