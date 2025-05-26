package com.example.flomi;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;

public class DiaryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_detail);

        TextView numView = findViewById(R.id.num);
        TextView titleView = findViewById(R.id.diary_content1);
        TextView contentView = findViewById(R.id.diary_content2);
        ImageView diaryImage = findViewById(R.id.diary_image);

        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String imageUri = intent.getStringExtra("imageUri");

        numView.setText(number + ".");
        titleView.setText(title);
        contentView.setText(content);

        if (imageUri != null && !imageUri.isEmpty()) {
            try {
                Uri uri = Uri.parse(imageUri);
                ContentResolver resolver = getContentResolver();
                InputStream inputStream = resolver.openInputStream(uri);
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    diaryImage.setImageBitmap(bitmap);
                    inputStream.close();
                } else {
                    diaryImage.setImageResource(R.drawable.baseline_add_24);
                }
            } catch (Exception e) {
                e.printStackTrace();
                diaryImage.setImageResource(R.drawable.baseline_add_24);
            }
        } else {
            diaryImage.setImageResource(R.drawable.baseline_add_24);
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
