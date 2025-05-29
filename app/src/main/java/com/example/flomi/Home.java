package com.example.flomi;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.DiaryDao;
import com.example.flomi.data.DiaryEntity;

public class Home extends AppCompatActivity {

    private AppDatabase db;

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

        db = AppDatabase.getInstance(getApplicationContext());

        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView dateTv = findViewById(R.id.date);
        TextView useItemTv = findViewById(R.id.use_item);
        TextView contextTv = findViewById(R.id.context);

        // 최신 일기 비동기 로딩
        new AsyncTask<Void, Void, DiaryEntity>() {
            @Override
            protected DiaryEntity doInBackground(Void... voids) {
                DiaryDao diaryDao = db.diaryDao();
                return diaryDao.getLatestDiary();
            }

            @Override
            protected void onPostExecute(DiaryEntity diary) {
                if (diary != null) {
                    // id가 int이므로 문자열로 변환하여 세팅
                    dateTv.setText(String.valueOf(diary.getId()));
                    useItemTv.setText(diary.getTitle());
                    contextTv.setText(diary.getContent());

                    String imageUri = diary.getImageUri();
                    if (imageUri != null && !imageUri.isEmpty()) {
                        imageView2.setImageURI(Uri.parse(imageUri));
                    }
                } else {
                    dateTv.setText("작성된 일기가 없습니다.");
                    useItemTv.setText("");
                    contextTv.setText("");
                }
            }
        }.execute();

        ImageButton category = findViewById(R.id.category);
        category.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Category.class);
            startActivity(intent);
        });

        ImageButton diary = findViewById(R.id.diary);
        diary.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, DiaryList.class);
            startActivity(intent);
        });

        ImageButton go_diary = findViewById(R.id.go_diary);
        go_diary.setOnClickListener(view -> {
            new Thread(() -> {
                DiaryEntity latestDiary = db.diaryDao().getLatestDiary(); // 최신 일기 가져오는 메서드 (DAO에 구현 필요)

                runOnUiThread(() -> {
                    Intent intent;
                    if (latestDiary != null) {
                        // 최신 일기 상세보기 화면이 있다고 가정
                        intent = new Intent(Home.this, DiaryDetail.class);
                        intent.putExtra("diary_id", latestDiary.getId());
                    } else {
                        // 없으면 새 일기 작성 화면으로 이동
                        intent = new Intent(Home.this, Diary.class);
                    }
                    startActivity(intent);
                });
            }).start();
        });

    }
}
