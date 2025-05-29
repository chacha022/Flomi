package com.example.flomi.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.flomi.R;

import java.util.Date;

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

    public Date createdAt;  // 추가된 필드


    // 게터 추가 (필요한 필드만)
    public int getProductId() {
        return productId;
    }
    public String getCompany() { return company; }
    public String getName() { return name; }
    public String getConcerns() { return concerns; }
    public String getEfficacy() { return efficacy; }

    public String getContent() { return content; }
    public String getCompany_productCount() { return company_productCount; }
    public String getImage() { return image; }

    // 이미지가 drawable 리소스 id 문자열이라면 정수 변환, 아니면 기본 이미지 리턴
    public int getImageResId() {
        try {
            return Integer.parseInt(image);
        } catch (NumberFormatException e) {
            return R.drawable.light; // 기본 이미지
        }
    }

}


