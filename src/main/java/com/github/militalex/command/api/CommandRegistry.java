package com.github.militalex.command.api;

import com.github.militalex.command.HelpCommand;
import com.github.militalex.command.LinphatorCommand;
import com.github.militalex.command.WebsiteCommand;
import com.github.militalex.command.music.*;
import com.github.militalex.command.ShutdownCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    public static final String CMD_PREFIX = "!tdv ";

    // Singleton Pattern
    private static CommandRegistry registry;

    public static CommandRegistry getInstance(){
        if (registry == null) registry = new CommandRegistry();
        return registry;
    }

    private Map<String, Command> commandMap = new HashMap();

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void registerCommands(){
        final ShutdownCommand shutdownCommand = new ShutdownCommand();
        final HelpCommand helpCommand = new HelpCommand();
        final PlayYtCommand playYtCommand = new PlayYtCommand();
        final PlayLocalCommand playLocalCommand = new PlayLocalCommand();
        final MusicListCommand musicListCommand = new MusicListCommand();
        final StopCommand stopCommand = new StopCommand();
        final SkipCommand skipCommand = new SkipCommand();
        final VolumeCommand volumeCommand = new VolumeCommand();
        final LinphatorCommand linphatorCommand = new LinphatorCommand();
        final WebsiteCommand websiteCommand = new WebsiteCommand();

        commandMap.put(shutdownCommand.getPrefix(), shutdownCommand);
        commandMap.put(helpCommand.getPrefix(), helpCommand);
        commandMap.put(playYtCommand.getPrefix(), playYtCommand);
        commandMap.put(playLocalCommand.getPrefix(), playLocalCommand);
        commandMap.put(musicListCommand.getPrefix(), musicListCommand);
        commandMap.put(stopCommand.getPrefix(), stopCommand);
        commandMap.put(skipCommand.getPrefix(), skipCommand);
        commandMap.put(volumeCommand.getPrefix(), volumeCommand);
        commandMap.put(linphatorCommand.getPrefix(), linphatorCommand);
        commandMap.put(websiteCommand.getPrefix(), websiteCommand);
    }
}
