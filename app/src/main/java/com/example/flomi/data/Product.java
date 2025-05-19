package com.example.flomi.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productId;

    public String company;

    public String name;
    public String category;
    public String skinTypes;  // 예: "건성,지성"
    public String concerns;   // 예: "보습,트러블"
    public String efficacy;
    public String company_productCount;

    public String content;
    public String image;

}