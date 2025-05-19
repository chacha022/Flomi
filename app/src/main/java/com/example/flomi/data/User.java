package com.example.flomi.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    public String name;

    public String gender;

    public Integer birth;
    public String skinType;    // 예: "건성"
    public String concerns;    // 예: "트러블,홍조"
}
