package com.github.militalex.command.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandPattern {

    private final List<CommandNode> head = new ArrayList<>();

    public CommandPattern(){

    }

    public CommandPattern(CommandNode... nodes){
        head.addAll(List.of(nodes));
    }

    public List<CommandNode> getHead() {
        return head;
    }

    public String getRegex(){
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < head.size(); i++){
            regex(stringBuilder, head.get(i));
            if (i < head.size() - 1) stringBuilder.append("|");
        }

        return stringBuilder.toString();
    }

    private void regex(StringBuilder stringBuilder, CommandNode node){
        if (node == null) return;

        stringBuilder.append("(");
        stringBuilder.append(node.getCommandUnit().getRegex());

        for (int i = 0; i < node.getChildren().size(); i++){
            if (i == 0) stringBuilder.append(" ");
            regex(stringBuilder, node.children.get(i));
            if (i < node.getChildren().size() - 1) stringBuilder.append("|");
        }

        stringBuilder.append(")");
    }

    public String getSimple(){
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < head.size(); i++){
            simple(stringBuilder, head.get(i));
            if (i < head.size() - 1) stringBuilder.append("|");
        }

        return stringBuilder.toString();
    }

    private void simple(StringBuilder stringBuilder, CommandNode node){
        if (node == null) return;

        stringBuilder.append("< ");
        stringBuilder.append(node.getCommandUnit().getSimple());

        for (int i = 0; i < node.getChildren().size(); i++){
            if (i == 0) stringBuilder.append(" ");
            simple(stringBuilder, node.children.get(i));
            if (i < node.getChildren().size() - 1) stringBuilder.append("|");
        }

        stringBuilder.append(" >");
    }

    public static class CommandNode{
        private final CommandUnit commandUnit;
        private final List<CommandNode> children = new ArrayList<>();

        public CommandNode(CommandUnit commandUnit) {
            this.commandUnit = commandUnit;
        }

        public CommandUnit getCommandUnit() {
            return commandUnit;
        }

        public List<CommandNode> getChildren() {
            return children;
        }
    }
}
