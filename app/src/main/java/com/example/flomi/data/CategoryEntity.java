package com.example.flomi.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String checkedConditions; // 체크박스 선택값들 (콤마로 구분된 문자열)

    public CategoryEntity(String checkedConditions) {
        this.checkedConditions = checkedConditions;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getConditions() { return checkedConditions; }
    public void setConditions(String conditions) { this.checkedConditions = conditions; }
}
