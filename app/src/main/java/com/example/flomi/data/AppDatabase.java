package com.example.flomi.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SurveyResponse.class, Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract SurveyDao surveyDao();
    public abstract ProductDao productDao(); // 추가

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "survey-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}