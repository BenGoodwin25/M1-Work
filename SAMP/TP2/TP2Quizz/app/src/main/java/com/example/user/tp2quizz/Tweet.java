package com.example.user.tp2quizz;

public class Tweet {
    private String pseudo;
    private String text;

    public Tweet(String pseudo, String text) {
        this.pseudo = pseudo;
        this.text = text;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}