package com.github.militalex.command.music;

import com.github.militalex.command.api.Command;
import com.github.militalex.command.api.CommandRegistry;
import com.github.militalex.command.api.EmptyPattern;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MusicListCommand extends Command {


    public MusicListCommand() {
        super("music", List.of(Permission.MESSAGE_SEND), new EmptyPattern(), "Zeigt alle von Linphator verfügbaren Musikstücke an.");
    }

    @Override
    protected void execute(Member member, Member self, TextChannel channel, Message message) {
        final Path musicPath = Path.of("assets/music");

        final EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GRAY);
        builder.setTitle("Von Linphator gebaute Musik");
        builder.setDescription("Zum anhören den \"" + CommandRegistry.CMD_PREFIX + " play\" command nutzen.");

        final String url = "https://tdv.minecraftmusical.com/assets/textures/LinLogo.jpg";
        builder.setThumbnail(url);

        final StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(Objects.requireNonNull(musicPath.toFile().list())).forEach(s -> stringBuilder.append(s).append("\n"));

        builder.addField("Musik", stringBuilder.toString(), false);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
