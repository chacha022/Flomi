package com.example.flomi.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyDao {
    @Insert
    Long insert(SurveyResponse response);

    @Query("SELECT * FROM survey_response")
    List<SurveyResponse> getAll();

    @Query("SELECT * FROM survey_response ORDER BY id DESC LIMIT 1")
    SurveyResponse getLatestResponse();
}

