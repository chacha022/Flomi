package com.example.flomi;

public class DiaryItem {
    private String number;
    private String title;
    private String content;

    public DiaryItem(String number, String title, String content) {
        this.number = number;
        this.title = title;
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
