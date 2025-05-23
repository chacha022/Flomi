package com.example.flomi.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// DiaryEntity, SurveyResponse, Product 3가지 엔티티 포함, 버전은 3으로 증가
@Database(entities = {SurveyResponse.class, Product.class, DiaryEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract SurveyDao surveyDao();
    public abstract ProductDao productDao();
    public abstract DiaryDao diaryDao();  // DiaryDao 추가

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flome-db")
                    .fallbackToDestructiveMigration()  // 임시 개발용, 실제 배포시 마이그레이션 필요
                    .build();
        }
        return instance;
    }
}