package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiaryDao {

    @Insert
    void insert(DiaryEntity diary); // 정상

    @Query("SELECT * FROM diary_table ORDER BY id DESC")
    List<DiaryEntity> getAllDiaries(); // 정상

    @Query("SELECT * FROM diary_table WHERE id = :id LIMIT 1")
    DiaryEntity getDiaryById(int id);

    @Query("SELECT * FROM diary_table ORDER BY id DESC LIMIT 1")
    DiaryEntity getLatestDiary(); // 정상
}

