package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Insert
    void insertAll(List<Product> products);  // 추가

    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    @Query("DELETE FROM product")
    void deleteAll();  // 선택: 중복 저장 방지

    @Query("SELECT * FROM product WHERE skinTypes LIKE '%' || :skinType || '%' AND concerns LIKE '%' || :concern || '%'")
    List<Product> getRecommendedProducts(String skinType, String concern);  // 주석 해제
}

