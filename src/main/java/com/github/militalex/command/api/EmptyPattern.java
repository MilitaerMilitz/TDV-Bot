package com.github.militalex.command.api;

public class EmptyPattern extends CommandPattern{

    @Override
    public String getRegex() {
        return "";
    }

    @Override
    public String getSimple() {
        return "";
    }
}
