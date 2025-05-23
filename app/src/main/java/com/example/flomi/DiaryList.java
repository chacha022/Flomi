package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.DiaryEntity;

import java.util.ArrayList;
import java.util.List;

public class DiaryList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_list);

        // 시스템 인셋 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 홈 버튼
        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Home.class);
            startActivity(intent);
        });

        // 카테고리 버튼
        ImageButton category = findViewById(R.id.category);
        category.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Category.class);
            startActivity(intent);
        });

        // 글쓰기 버튼
        Button write = findViewById(R.id.write);
        write.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Diary.class);
            startActivity(intent);
        });

        // RecyclerView 설정
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 저장된 다이어리 불러오기
        new Thread(() -> {
            // Room DB 인스턴스 가져오기
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<DiaryEntity> diaryEntities = db.diaryDao().getAllDiaries(); // 전체 데이터 가져오기

            // DiaryItem 리스트로 변환
            List<DiaryItem> diaryList = new ArrayList<>();
            int i = 1;
            for (DiaryEntity diary : diaryEntities) {
                diaryList.add(new DiaryItem(String.valueOf(i), diary.getTitle(), diary.getContent(),diary.getImageUri()));
                i++;
            }

            // 어댑터 설정 (UI 스레드에서 실행해야 함)
            runOnUiThread(() -> {
                DiaryAdapter adapter = new DiaryAdapter(diaryList);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}