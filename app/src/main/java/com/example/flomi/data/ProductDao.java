package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM product WHERE skinTypes LIKE '%' || :skinType || '%' AND concerns LIKE '%' || :concern || '%'")
    List<Product> getRecommendedProducts(String skinType, String concern);
}
