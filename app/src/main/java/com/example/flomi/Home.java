package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton category = findViewById(R.id.category);

        category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Home.this, Category.class);
                startActivity(intent);
            }
        });

        ImageButton diary = findViewById(R.id.diary);

        diary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Home.this, DiaryList.class);
                startActivity(intent);
            }
        });

        ImageButton go_diary = findViewById(R.id.go_diary);

        go_diary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Home.this, Diary.class);
                startActivity(intent);
            }
        });
    }
}