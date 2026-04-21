package com.example.miniquiz;

public class Question {
    String question;
    String a, b, c;
    String correct;

    public Question(String question, String a, String b, String c, String correct) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.correct = correct;
    }
}

