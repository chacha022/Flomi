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

public class Diary extends AppCompatActivity {

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

        ImageButton home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Diary.this, Home.class);
                startActivity(intent);
            }
        });

        ImageButton category = findViewById(R.id.category);

        category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Diary.this, Category.class);
                startActivity(intent);
            }
        });
    }
}