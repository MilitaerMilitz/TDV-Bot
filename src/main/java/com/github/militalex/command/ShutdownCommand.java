package com.github.militalex.command;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.EmptyPattern;
import com.github.militalex.main.TdvBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import spark.Spark;

import java.util.Collections;
import java.util.List;

public final class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", List.of(Permission.ADMINISTRATOR), new EmptyPattern(), "Fährt Discord Bot runter.");
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        channel.sendMessage("Bot wird heruntergefahren ...").queue(msg -> {
            Spark.stop();
            TdvBot.getInstance().shutdown();
        });
    }
}
