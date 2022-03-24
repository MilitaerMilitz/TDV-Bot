package com.github.militalex.command.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.github.militalex.command.api.CommandPattern.CommandNode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandPatternTest {

    public CommandPattern pattern;

    @BeforeEach
    public void setup(){
        pattern = new CommandPattern();
    }

    @Test
    public void simpleCommand(){
        pattern.getHead().add(new CommandNode(CommandUnit.STRING));

        final String regex = pattern.getRegex();
        assertEquals("(.+)", regex);
        assertTrue("sdfg".matches(regex));
    }

    @Test
    public void headOptionCommand(){
        final List<CommandNode> head = pattern.getHead();
        head.add(new CommandNode(CommandUnit.STRING));
        head.add(new CommandNode(CommandUnit.STRING));
        head.add(new CommandNode(CommandUnit.STRING));
        head.add(new CommandNode(CommandUnit.STRING));

        final String regex = pattern.getRegex();
        //assertEquals("((.+)|(.+)|(.+)|(.+))", regex);
        assertTrue("sdfg".matches(regex));
    }

    @Test
    public void deepOptionCommand(){
        final List<CommandNode> head = pattern.getHead();

        CommandNode pre;

        head.add(pre = new CommandNode(CommandUnit.STRING));

        for (int i = 0; i < 3; i++){
            final CommandNode node = new CommandNode(CommandUnit.STRING);
            pre.getChildren().add(node);
            pre = node;
        }

        final String regex = pattern.getRegex();
        //assertEquals("((.+) (.+) (.+) (.+))", regex);
        assertTrue("sdfg dfgh dhj dgfhdhg".matches(regex));
    }

    @Test
    public void mixOptionCommand(){
        final List<CommandNode> head = pattern.getHead();

        CommandNode pre;
        head.add(pre = new CommandNode(CommandUnit.STRING));

        for (int i = 0; i < 2; i++){
            final CommandNode node = new CommandNode(CommandUnit.STRING);
            pre.getChildren().add(node);
            pre = node;
        }

        head.add(pre = new CommandNode(CommandUnit.STRING));

        for (int i = 0; i < 3; i++){
            final CommandNode node = new CommandNode(CommandUnit.STRING);
            pre.getChildren().add(node);
            pre = node;
        }

        head.add(pre = new CommandNode(CommandUnit.STRING));

        for (int i = 0; i < 2; i++){
            final CommandNode node = new CommandNode(CommandUnit.STRING);
            pre.getChildren().add(node);
            pre = node;
        }

        final String regex = pattern.getRegex();
        //assertEquals("(((.+) (.+) (.+))|((.+) (.+) (.+) (.+))|((.+) (.+) (.+)))", regex);
        assertTrue("sdfgh dasfj sdf".matches(regex));
    }

    @Test
    public void mixOptionCommand2(){
        final List<CommandNode> head = pattern.getHead();
        final List<CommandNode> nodes = new ArrayList<>();

        for (int i = 0; i < 26; i++){
            nodes.add(new CommandNode(CommandUnit.STRING));
        }

        head.add(nodes.get(24));
        head.add(nodes.get(25));

        //------

        nodes.get(24).getChildren().add(nodes.get(11));
        nodes.get(24).getChildren().add(nodes.get(15));

        nodes.get(25).getChildren().add(nodes.get(23));

        final String regex = pattern.getRegex();
        //assertEquals("((.+) ((.+)|(.+))|((.+) (.+)))", regex);
        assertTrue("sdfgh dasfj sdf".matches(regex));
    }


    public void complicatedMixOptionCommand(){
        final List<CommandNode> head = pattern.getHead();
        final List<CommandNode> nodes = new ArrayList<>();

        for (int i = 0; i < 26; i++){
            nodes.add(new CommandNode(CommandUnit.STRING));
        }

        head.add(nodes.get(24));
        head.add(nodes.get(25));

        //------

        nodes.get(24).getChildren().add(nodes.get(11));
        nodes.get(24).getChildren().add(nodes.get(15));

        nodes.get(25).getChildren().add(nodes.get(23));

        //------

        nodes.get(11).getChildren().add(nodes.get(10));
        nodes.get(15).getChildren().add(nodes.get(13));
        nodes.get(15).getChildren().add(nodes.get(14));

        nodes.get(23).getChildren().add(nodes.get(20));
        nodes.get(23).getChildren().add(nodes.get(21));
        nodes.get(23).getChildren().add(nodes.get(22));

        //------

        nodes.get(10).getChildren().add(nodes.get(9));
        nodes.get(13).getChildren().add(nodes.get(12));

        nodes.get(21).getChildren().add(nodes.get(18));
        nodes.get(22).getChildren().add(nodes.get(19));

        //------

        nodes.get(9).getChildren().add(nodes.get(8));

        nodes.get(18).getChildren().add(nodes.get(16));
        nodes.get(18).getChildren().add(nodes.get(17));

        //------

        nodes.get(8).getChildren().add(nodes.get(5));
        nodes.get(8).getChildren().add(nodes.get(6));
        nodes.get(8).getChildren().add(nodes.get(7));

        //------

        nodes.get(5).getChildren().add(nodes.get(2));
        nodes.get(5).getChildren().add(nodes.get(3));
        nodes.get(6).getChildren().add(nodes.get(4));

        //------

        nodes.get(4).getChildren().add(nodes.get(1));

        final String regex = pattern.getRegex();
        assertEquals("((.+)|())", regex);
        assertTrue("sdfgh dasfj sdf".matches(regex));

    }
}