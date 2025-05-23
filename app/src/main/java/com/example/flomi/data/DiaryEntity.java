package com.example.flomi.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_table")
public class DiaryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String content;

    private String imageUri; // 또는 imagePath

    // 생성자
    public DiaryEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUri(){
        return imageUri;
    }

    // Setter (Room이 내부적으로 사용)
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
