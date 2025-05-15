package com.example.flomi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String gender = getIntent().getStringExtra("gender");
        int birthYear = getIntent().getIntExtra("birthYear", 0);
        String skinType = getIntent().getStringExtra("skinType");
        String personalColor = getIntent().getStringExtra("personalColor");
        String skinConcern = getIntent().getStringExtra("skinConcern");

        SurveyDBHelper dbHelper = new SurveyDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("gender", gender);
        values.put("birth_year", birthYear);
        values.put("skin_type", skinType);
        values.put("personal_color", personalColor);
        values.put("skin_concern", skinConcern);

        long newRowId = db.insert(SurveyDBHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "설문이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
        }

        // 버튼 리스너는 그대로 유지
        ImageButton next = findViewById(R.id.survey4_btn_next1);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Survey4.this, Home.class);
                startActivity(intent);
            }
        });

        ImageButton back = findViewById(R.id.survey4_backButton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Survey4.this, Survey3.class);
                startActivity(intent);
            }
        });
    }
}