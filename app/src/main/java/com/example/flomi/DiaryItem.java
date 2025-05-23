package com.example.flomi;

public class DiaryItem {
    private String number;
    private String title;
    private String content;
    private String imageUri;  // String 타입으로 저장 가정

    public DiaryItem(String number, String title, String content, String imageUri) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.imageUri = imageUri;
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
