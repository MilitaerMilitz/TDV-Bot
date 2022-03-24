package com.github.militalex.command;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.EmptyPattern;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.nio.file.Path;
import java.util.List;

public class LinphatorCommand extends Command {
    public LinphatorCommand() {
        super("Linphator", List.of(Permission.MESSAGE_EMBED_LINKS), new EmptyPattern(), "Ein Linphator wie er leibt und lebt.");
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        channel.sendFile(Path.of("assets/textures/LinLive.png").toFile()).queue();
    }
}
