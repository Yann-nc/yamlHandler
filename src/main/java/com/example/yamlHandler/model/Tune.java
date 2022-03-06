package com.example.yamlHandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Tune {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String author;

    public Tune() {
        this.id = 0;
        this.title = "defaut title";
        this.author = "default author";
    }

    @Override
    public String toString(){
        return "Music " + getId()
                + ": " + getTitle()
                + " by: " + getAuthor()
                + "\n";
    }

//    public Tune(int id, String title, String author) {
//        this.id = id;
//        this.title = title;
//        this.author = author;
//    }
}
