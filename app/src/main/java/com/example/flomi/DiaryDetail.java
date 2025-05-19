package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DiaryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_detail);

        TextView numView = findViewById(R.id.num);
        TextView titleView = findViewById(R.id.diary_content1);
        TextView contentView = findViewById(R.id.diary_content2);

        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        numView.setText(number+".");
        titleView.setText(title);
        contentView.setText(content);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 목록 버튼
        Button list = findViewById(R.id.list);
        list.setOnClickListener(view -> {
            Intent i = new Intent(DiaryDetail.this, DiaryList.class);
            startActivity(i);
        });
    }
}