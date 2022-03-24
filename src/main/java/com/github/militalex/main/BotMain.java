package com.github.militalex.main;

import spark.Spark;

import javax.security.auth.login.LoginException;

public class BotMain {
    public static void main(String[] args) {
        TdvBot.getInstance();

        // Working on Port 4567
        /*Spark.get("/notification", (request, response) -> {
            return "Hallo Welt";
        });*/


    }
}
