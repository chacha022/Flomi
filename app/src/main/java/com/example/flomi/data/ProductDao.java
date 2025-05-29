package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM product WHERE skinTypes LIKE '%' || :skinType || '%' OR skinTypes LIKE '%모든 피부 타입%' LIMIT 5")
    List<Product> getTop5ProductsBySkinType(String skinType);

    @Query("SELECT * FROM product WHERE productId = :id LIMIT 1")
    Product getProductById(int id);

    @Update
    void updateProduct(Product product); // ⬅️ 추가

    @Query("SELECT * FROM product ORDER BY createdAt DESC LIMIT 1")
    Product getLatestProduct();
}

