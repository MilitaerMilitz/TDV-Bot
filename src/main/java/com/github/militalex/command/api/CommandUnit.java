package com.github.militalex.command.api;

public enum CommandUnit {
    STRING(".+", "einfacher Text"),
    PERCENT("(100)|([0-9]?[0-9])", "Zahl zwischen 0 und 100"),
    INT("\\d+", "einfache Zahl");


    private final String regex;
    private final String simple;

    CommandUnit(String regex, String simple){
        this.regex = regex;
        this.simple = simple;
    }

    public String getSimple() {
        return simple;
    }

    public String getRegex() {
        return regex;
    }

    @Override
    public String toString() {
        return regex;
    }
}
