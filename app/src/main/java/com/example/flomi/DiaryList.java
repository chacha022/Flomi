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
import com.example.flomi.data.DiaryItem;

import java.util.ArrayList;
import java.util.List;

public class DiaryList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Home.class);
            startActivity(intent);
        });

        ImageButton category = findViewById(R.id.category);
        category.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Category.class);
            startActivity(intent);
        });

        Button write = findViewById(R.id.write);
        write.setOnClickListener(view -> {
            Intent intent = new Intent(DiaryList.this, Diary.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadDiaries(recyclerView);
    }

    private void loadDiaries(RecyclerView recyclerView) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<DiaryEntity> diaryEntities = db.diaryDao().getAllDiaries();

            List<DiaryItem> diaryList = new ArrayList<>();
            int i = 1;
            for (DiaryEntity diary : diaryEntities) {
                diaryList.add(new DiaryItem(diary.getId(), String.valueOf(i), diary.getTitle(), diary.getContent(), diary.getImageUri()));
                i++;
            }

            runOnUiThread(() -> {
                DiaryAdapter adapter = new DiaryAdapter(diaryList);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        loadDiaries(recyclerView);
    }
}
