package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey1 extends AppCompatActivity {

    private String selectedGender = "";
    private int selectedYear = 0;
    private RadioGroup genderGroup;
    private EditText birthYearEditText;
    private int lastCheckedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        genderGroup = findViewById(R.id.radioGroup_gender);
        birthYearEditText = findViewById(R.id.numberEditText);

        // ▶ 이전 선택값 복원
        selectedGender = getIntent().getStringExtra("gender") != null ? getIntent().getStringExtra("gender") : "";
        selectedYear = getIntent().getIntExtra("birthYear", 0);

        if (selectedGender.equals("여성")) {
            genderGroup.check(R.id.survey1_female);
        } else if (selectedGender.equals("남성")) {
            genderGroup.check(R.id.survey1_male);
        }

        if (selectedYear != 0) {
            birthYearEditText.setText(String.valueOf(selectedYear));
        }

        // ▶ 성별 선택 토글 기능
        genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (lastCheckedId == checkedId) {
                genderGroup.clearCheck();
                selectedGender = "";
                lastCheckedId = -1;
            } else {
                RadioButton selected = findViewById(checkedId);
                selectedGender = selected.getText().toString();
                lastCheckedId = checkedId;
            }
        });

        ImageButton next = findViewById(R.id.survey1_btn_next1);
        next.setOnClickListener(view -> {
            String yearText = birthYearEditText.getText().toString();
            if (!yearText.isEmpty()) {
                selectedYear = Integer.parseInt(yearText);
                if (selectedYear < 1960 || selectedYear > 2025) {
                    Toast.makeText(Survey1.this, "1960년부터 2025년 사이로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return; // → 조건 안 맞으면 넘어가지 않음
                }
            } else {
                Toast.makeText(Survey1.this, "출생년도를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Survey1.this, Survey2.class);
            intent.putExtra("gender", selectedGender);
            intent.putExtra("birthYear", selectedYear);
            startActivity(intent);
        });

    }
}
