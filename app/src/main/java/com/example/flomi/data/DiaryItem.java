package com.example.flomi.data;

public class DiaryItem {
    private int id;  // DB PK
    private String number;
    private String title;
    private String content;
    private String imageUri;

    public DiaryItem(int id, String number, String title, String content, String imageUri) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.content = content;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
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

    public String getImageUri() {
        return imageUri;
    }
}
