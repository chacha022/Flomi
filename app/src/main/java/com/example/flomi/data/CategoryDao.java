package com.example.flomi.data;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(CategoryEntity categoryEntity);


    @Query("SELECT checkedConditions FROM category LIMIT 1")
    String getCheckedConditions();

    @Query("SELECT * FROM category ORDER BY id DESC LIMIT 1")
    CategoryEntity getLatestCategory();
}