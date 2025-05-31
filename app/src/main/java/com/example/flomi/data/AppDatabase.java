package com.example.flomi.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.flomi.R;

@Database(entities = {SurveyResponse.class, Product.class, DiaryEntity.class, CategoryEntity.class}, version = 12)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final int VERSION = 12;

    private static volatile AppDatabase instance;

    public abstract SurveyDao surveyDao();
    public abstract ProductDao productDao();
    public abstract DiaryDao diaryDao();
    public abstract CategoryDao categoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flomi-db")
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
                ProductDataLoader.loadAndStoreProducts(context, R.raw.product);
            }
        };
    }
}