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
import androidx.viewpager2.widget.ViewPager2;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.DiaryEntity;
import com.example.flomi.data.Product;
import com.example.flomi.data.SurveyResponse;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private AppDatabase db;
    private ViewPager2 viewPager;
    private List<Product> recommendedProducts;

    // 다이어리 뷰 변수 선언
    private TextView dateTextView, useItemTextView, contextTextView;
    private ImageView diaryImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        db = AppDatabase.getInstance(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);

        // 다이어리 텍스트뷰 연결
        dateTextView = findViewById(R.id.date);
        useItemTextView = findViewById(R.id.use_item);
        contextTextView = findViewById(R.id.context);
        diaryImageView = findViewById(R.id.imageView2);

        // 1) 제품 추천 ViewPager 불러오기 (AsyncTask)
        new AsyncTask<Void, Void, DiaryEntity>() {
            @Override
            protected DiaryEntity doInBackground(Void... voids) {
                return db.diaryDao().getLatestDiary();  // 최근 일기 1개 가져오기
            }

            @Override
            protected void onPostExecute(DiaryEntity latestDiary) {
                if (latestDiary != null) {
                    // ID를 날짜 위치에 보여주기
                    dateTextView.setText(String.valueOf(latestDiary.getId()));

                    // 제목과 내용을 표시
                    useItemTextView.setText(latestDiary.getTitle());
                    contextTextView.setText(latestDiary.getContent());

                    // 이미지 URI를 ImageView에 적용
                    String uriString = latestDiary.getImageUri();
                    if (uriString != null && !uriString.isEmpty()) {
                        Uri imageUri = Uri.parse(uriString);
                        diaryImageView.setImageURI(imageUri);
                    } else {
                        // 이미지가 없을 경우 기본 이미지 설정
                        diaryImageView.setImageResource(R.drawable.light);
                    }
                } else {
                    // 데이터가 없을 경우 기본 메시지 설정
                    dateTextView.setText("N/A");
                    useItemTextView.setText("작성된 제목 없음");
                    contextTextView.setText("작성된 내용 없음");
                    diaryImageView.setImageResource(R.drawable.light);
                }
            }
        }.execute();

        // 2) 최신 다이어리 불러와서 뷰에 표시 (AsyncTask)
        new AsyncTask<Void, Void, DiaryEntity>() {
            @Override
            protected DiaryEntity doInBackground(Void... voids) {
                return db.diaryDao().getLatestDiary();
            }

            @Override
            protected void onPostExecute(DiaryEntity latestDiary) {
                if (latestDiary != null) {
                    dateTextView.setText(String.valueOf(latestDiary.getId()));
                    useItemTextView.setText(latestDiary.getTitle());
                    contextTextView.setText(latestDiary.getContent());
                } else {
                    dateTextView.setText("작성된 다이어리가 없습니다.");
                    useItemTextView.setText("");
                    contextTextView.setText("");
                }
            }
        }.execute();

        // 하단 메뉴 버튼 이벤트
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
                DiaryEntity latestDiary = db.diaryDao().getLatestDiary();

                runOnUiThread(() -> {
                    Intent intent;
                    if (latestDiary != null) {
                        intent = new Intent(Home.this, DiaryDetail.class);
                        intent.putExtra("diary_id", latestDiary.getId());
                    } else {
                        intent = new Intent(Home.this, Diary.class);
                    }
                    startActivity(intent);
                });
            }).start();
        });
    }
}
