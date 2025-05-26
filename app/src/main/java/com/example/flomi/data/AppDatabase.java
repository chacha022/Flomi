package com.example.flomi.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SurveyResponse.class, Product.class, DiaryEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public static final int VERSION = 3;  // 버전 상수 추가

    private static AppDatabase instance;

    public abstract SurveyDao surveyDao();
    public abstract ProductDao productDao();
    public abstract DiaryDao diaryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flome-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
