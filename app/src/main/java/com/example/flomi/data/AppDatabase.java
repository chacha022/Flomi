package com.example.flomi.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.flomi.R;

@Database(entities = {SurveyResponse.class, Product.class, DiaryEntity.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    public static final int VERSION = 5;  // 현재 데이터베이스 버전에 맞춤

    private static volatile AppDatabase instance;

    public abstract SurveyDao surveyDao();
    public abstract ProductDao productDao();
    public abstract DiaryDao diaryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flome-db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback(context))
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback(Context context) {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // 데이터베이스가 처음 생성될 때 JSON 데이터를 읽어와 저장
                // 별도 스레드에서 실행됨
                ProductDataLoader.loadAndStoreProducts(context, R.raw.product);
            }
        };
    }
}
