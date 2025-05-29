package com.example.flomi;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.DiaryEntity;

import java.io.InputStream;

public class DiaryDetail extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_detail);

        TextView numView = findViewById(R.id.num);
        TextView titleView = findViewById(R.id.diary_content1);
        TextView contentView = findViewById(R.id.diary_content2);
        ImageView diaryImage = findViewById(R.id.diary_image);

        db = AppDatabase.getInstance(getApplicationContext());

        int diaryId = getIntent().getIntExtra("diary_id", -1);
        if (diaryId != -1) {
            new AsyncTask<Integer, Void, DiaryEntity>() {
                @Override
                protected DiaryEntity doInBackground(Integer... ids) {
                    return db.diaryDao().getDiaryById(ids[0]);
                }

                @Override
                protected void onPostExecute(DiaryEntity diary) {
                    if (diary != null) {
                        numView.setText(diary.getId() + ".");
                        titleView.setText(diary.getTitle());
                        contentView.setText(diary.getContent());

                        String imageUri = diary.getImageUri();
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
                    }
                }
            }.execute(diaryId);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.list).setOnClickListener(view -> {
            Intent i = new Intent(DiaryDetail.this, DiaryList.class);
            startActivity(i);
        });
    }
}
