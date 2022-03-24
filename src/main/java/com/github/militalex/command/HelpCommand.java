package com.github.militalex.command;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandRegistry;
import com.github.militalex.command.api.EmptyPattern;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.List;

public final class HelpCommand extends Command {

    public HelpCommand() {
        super("help", List.of(Permission.MESSAGE_SEND), new EmptyPattern(), "Zeigt Liste dir verfügbarer Commands an.");
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {

        final EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Hilfe");
        builder.setColor(0x820000);
        builder.setDescription("Du kannst folgende Commands nutzen:");


        CommandRegistry.getInstance().getCommandMap().values().stream()
                .filter(command -> command.canUse(member))
                .forEach(command -> {
                    builder.addField(command.getSimple(), command.getDescription(), false);
                });

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
