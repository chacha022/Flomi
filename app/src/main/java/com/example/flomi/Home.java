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
import com.example.flomi.data.DiaryDao;
import com.example.flomi.data.DiaryEntity;

import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    private AppDatabase db;
    private ViewPager2 viewPager;
    private List<String> imageFileNames;

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

        // 🔹 ViewPager2 설정
        viewPager = findViewById(R.id.viewPager);
        imageFileNames = Arrays.asList("Ampoule_Serum.jpg", "Ceramide_Ato_Lotion.jpg", "cica_mask.jpg", "Cleansing_Balm.jpg", "Cleansing_Milk.jpg"); // assets/images 폴더에 있어야 함
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageFileNames);
        viewPager.setAdapter(adapter);

        // 일기 정보
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView dateTv = findViewById(R.id.date);
        TextView useItemTv = findViewById(R.id.use_item);
        TextView contextTv = findViewById(R.id.context);

        new AsyncTask<Void, Void, DiaryEntity>() {
            @Override
            protected DiaryEntity doInBackground(Void... voids) {
                DiaryDao diaryDao = db.diaryDao();
                return diaryDao.getLatestDiary();
            }

            @Override
            protected void onPostExecute(DiaryEntity diary) {
                if (diary != null) {
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
