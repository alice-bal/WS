package com.suraifokkusu.model;

import java.util.List;

public class Replacement {
    private String replacement;
    private String source;

    public Replacement(){}
    public Replacement(String replacement, String source) {
        this.replacement = replacement;
        this.source = source;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    @Override
    public String toString() {
        return "Replacement{" +
                "replacement='" + replacement + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
