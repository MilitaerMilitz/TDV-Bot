package com.github.militalex.command.api;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command{

    private String prefix;
    private List<Permission> neededPermissions;
    private CommandPattern commandPattern;
    private String description;

    protected Command(String prefix, List<Permission> neededPermissions, CommandPattern pattern, String description){
        this.prefix = prefix;
        this.neededPermissions = neededPermissions;
        this.commandPattern = pattern;
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Permission> getNeededPermissions() {
        return neededPermissions;
    }

    public CommandPattern getCommandPattern() {
        return commandPattern;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getArgs(Message message){
        return Arrays.stream(message.getContentDisplay().split(" ")).filter(s -> !s.equals(CommandRegistry.CMD_PREFIX.trim())).filter(s -> !s.equals(prefix)).collect(Collectors.toList());
    }

    public String getCommandRegex(){
        return (CommandRegistry.CMD_PREFIX + prefix + " " + commandPattern.getRegex()).trim();
    }

    public String getSimple(){
        return (CommandRegistry.CMD_PREFIX + prefix + " " + commandPattern.getSimple()).trim();
    }

    public boolean canUse(Member member){
        return PermissionUtil.checkPermission(member, getNeededPermissions().toArray(new Permission[0]));
    }

    public void tryExecute(MessageReceivedEvent event){
        final Member member = event.getMember();

        if (member == null){
            event.getTextChannel().sendMessage("Der Bot beantwortet aus sicherheitstechnischen Gründen keine privaten Anfragen. " +
                    "Bitte versuche es auf unserem Discord Server noch einmal.").queue();
        }
        else if (!canUse(member)){
            event.getTextChannel().sendMessage("Du hast nicht die Berechtigung für diesen Command !").queue();
        }
        else if (!event.getMessage().getContentDisplay().matches(getCommandRegex())){
            wrongSyntaxMsg(event.getTextChannel());
        }
        else {
            execute(event.getMember(), event.getGuild().getSelfMember(), event.getTextChannel(), event.getMessage());
        }
    }

    protected abstract void execute(Member member, Member self, TextChannel channel, Message message);

    private void wrongSyntaxMsg(TextChannel channel){
        channel.sendMessage("Deine Eingabe ist nicht korrekt. \nDer richtige Syntax für deinen Command lautet: \n" + getSimple()).queue();
    }

    protected boolean verifyMemberForMusicCommand(Member member, Member self, TextChannel channel) {
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!memberVoiceState.inAudioChannel()){
            channel.sendMessage("Dieser Command ist nur für Leute im Sprachkanal.").queue();
            return false;
        }

        if (!selfVoiceState.inAudioChannel()){
            channel.sendMessage("Der Bot ist aktuell in keinem Sprachkanal").queue();
            return false;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("Um den Bot zu beenden musst du im selben Sprachkanal sein.").queue();
            return false;
        }
        return true;
    }
}
