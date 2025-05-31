package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.SurveyDao;
import com.example.flomi.data.SurveyResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Survey4 extends AppCompatActivity {

    private AppDatabase db;
    private SurveyDao surveyDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey4);

        // Room DB 인스턴스 생성
        db = AppDatabase.getInstance(getApplicationContext());
        surveyDao = db.surveyDao();

        String gender = getIntent().getStringExtra("gender");
        int birthYear = getIntent().getIntExtra("birthYear", 0);
        String skinType = getIntent().getStringExtra("skinType");
        String personalColor = getIntent().getStringExtra("personalColor");
        String skinConcern = getIntent().getStringExtra("skinConcern");

        // 설문 데이터 객체 생성
        SurveyResponse response = new SurveyResponse();
        response.gender = gender;
        response.birth_year = birthYear;
        response.skin_type = skinType;
        response.personal_color = personalColor;
        response.skin_concern = skinConcern;

        // DB 작업은 메인 스레드에서 하면 안 되므로 별도 스레드에서 실행
        executor.execute(() -> {
            long id = surveyDao.insert(response);
            runOnUiThread(() -> {
                if (id != -1) {
                    Toast.makeText(Survey4.this, "설문이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Survey4.this, "저장 실패", Toast.LENGTH_SHORT).show();
                }
            });
        });

        ImageButton next = findViewById(R.id.survey4_btn_next1);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(Survey4.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
