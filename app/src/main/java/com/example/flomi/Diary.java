package com.example.flomi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class Diary extends AppCompatActivity {

    MultiAutoCompleteTextView answer1, answer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        answer1 = findViewById(R.id.diary_answer1);
        answer2 = findViewById(R.id.diary_answer2);
        // 저장 버튼
        // 저장 버튼 클릭
        Button save = findViewById(R.id.save);
        save.setOnClickListener(view -> {
            String title = answer1.getText().toString().trim();
            String content = answer2.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty()) {
                // DiaryEntity 객체 생성

                DiaryDatabase db = DiaryDatabase.getInstance(getApplicationContext());
                DiaryEntity diary = new DiaryEntity(title, content);

                // Room DB에 데이터 저장 (백그라운드 스레드에서 실행해야 함)
                new Thread(() -> {
                    db.diaryDao().insert(diary);
                    runOnUiThread(() -> {
                        Intent intent = new Intent(Diary.this, DiaryList.class);
                        startActivity(intent);
                    });
                }).start();
            }

            // 리스트 화면으로 이동
            Intent intent = new Intent(Diary.this, DiaryList.class);
            startActivity(intent);
        });

    }
}