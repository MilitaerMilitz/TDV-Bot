package com.github.militalex.command.api;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)){
            String msg = event.getMessage().getContentDisplay();

            if (msg.startsWith(CommandRegistry.CMD_PREFIX)){
                msg = msg.replaceFirst(CommandRegistry.CMD_PREFIX, "");

                final CommandRegistry registry = CommandRegistry.getInstance();

                final Command command = registry.getCommandMap().get(msg.split(" ")[0]);

                if (command == null) {
                    //bvasdhg
                }
                else command.tryExecute(event);
            }
        }
    }
}
