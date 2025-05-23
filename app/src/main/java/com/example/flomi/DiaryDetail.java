package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.net.Uri;
import android.widget.ImageView;


public class DiaryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_detail);

        TextView numView = findViewById(R.id.num);
        TextView titleView = findViewById(R.id.diary_title);  // 주의: id가 diary_title임
        MultiAutoCompleteTextView content1View = findViewById(R.id.diary_content1);
        MultiAutoCompleteTextView content2View = findViewById(R.id.diary_content2);
        ImageView diaryImage = findViewById(R.id.diary_image);

        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String title = intent.getStringExtra("title");
        String content1 = intent.getStringExtra("content1");  // 만약 여러 내용이 있다면
        String content2 = intent.getStringExtra("content2");
        String imageUri = intent.getStringExtra("imageUri");

        numView.setText(number + ".");
        titleView.setText(title);
        content1View.setText(content1);
        content2View.setText(content2);

        if (imageUri != null && !imageUri.isEmpty()) {
            diaryImage.setImageURI(Uri.parse(imageUri));
        } else {
            diaryImage.setImageResource(R.drawable.baseline_add_24); // 기본 이미지 예
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button list = findViewById(R.id.list);
        list.setOnClickListener(view -> {
            Intent i = new Intent(DiaryDetail.this, DiaryList.class);
            startActivity(i);
        });
    }
}