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

import com.example.flomi.data.ProductDao;
import com.example.flomi.data.Product;
import com.example.flomi.data.SurveyResponse;

public class Home extends AppCompatActivity {

    private AppDatabase db;
    private ViewPager2 viewPager;
    private List<String> imageFileNames;

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

        viewPager = findViewById(R.id.viewPager); // viewPager 초기화 누락 주의


        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                SurveyResponse survey = db.surveyDao().getLatestResponse(); // 이제 db는 null이 아님
                if (survey == null) return null;

                String skinType = survey.skin_type;
                ProductDao productDao = db.productDao();
                List<Product> matchedProducts = productDao.getTop5ProductsBySkinType(skinType);

                return matchedProducts.stream()
                        .map(product -> product.image)
                        .toList();
            }

            @Override
            protected void onPostExecute(List<String> recommendedImages) {
                if (recommendedImages != null && !recommendedImages.isEmpty()) {
                    imageFileNames = recommendedImages;
                } else {
                    imageFileNames = Arrays.asList("default1.jpg", "default2.jpg");
                }

                ImagePagerAdapter adapter = new ImagePagerAdapter(Home.this, imageFileNames);
                viewPager.setAdapter(adapter);
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
