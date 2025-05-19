package com.example.flomi.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "survey_response")
public class SurveyResponse {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String gender;
    public int birth_year;
    public String skin_type;
    public String personal_color;
    public String skin_concern;
}

