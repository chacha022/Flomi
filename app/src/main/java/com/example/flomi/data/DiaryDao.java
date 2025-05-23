package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    void insert(DiaryEntity diary);

    @Query("SELECT * FROM diary_table ORDER BY id DESC")
    List<DiaryEntity> getAllDiaries();
}
