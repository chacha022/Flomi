package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        // 시스템 바 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 찾기 버튼
        Button find = findViewById(R.id.find);
        find.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, ItemList.class);
            startActivity(intent);
        });

        // 홈 버튼
        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, Home.class);
            startActivity(intent);
        });

        // 다이어리 버튼
        ImageButton diary = findViewById(R.id.diary);
        diary.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, DiaryList.class);
            startActivity(intent);
        });



    }


}