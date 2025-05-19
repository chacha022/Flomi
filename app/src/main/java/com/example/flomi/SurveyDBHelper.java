//package com.example.flomi;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class SurveyDBHelper extends SQLiteOpenHelper {
//
//    private static final String DB_NAME = "flomi_survey.db";
//    private static final int DB_VERSION = 1;
//
//    public static final String TABLE_NAME = "survey_response";
//
//    public SurveyDBHelper(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "gender TEXT, " +
//                "birth_year INTEGER, " +
//                "skin_type TEXT, " +
//                "personal_color TEXT, " +
//                "skin_concern TEXT)";
//        db.execSQL(CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//}