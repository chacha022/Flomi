package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey2 extends AppCompatActivity {

    private RadioButton[] skinButtons;
    private RadioButton[] colorButtons;
    private RadioButton selectedSkin = null;
    private RadioButton selectedColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey2);

        // 시스템 바 여백 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 다음 버튼 클릭 시 Survey3로 이동
        ImageButton next = findViewById(R.id.survey2_btn_next1);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(Survey2.this, Survey3.class);
            startActivity(intent);
        });

        // 뒤로 버튼 클릭 시 Survey1로 이동
        ImageButton back = findViewById(R.id.survey2_backButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Survey2.this, Survey1.class);
            startActivity(intent);
        });

        // ✅ 라디오 버튼 토글 처리 추가

        // 피부 타입 버튼 모음
        skinButtons = new RadioButton[]{
                findViewById(R.id.survey2_type1),
                findViewById(R.id.survey2_type2),
                findViewById(R.id.survey2_type3),
                findViewById(R.id.survey2_type4),
                findViewById(R.id.survey2_type5)
        };

        // 퍼스널컬러 버튼 모음
        colorButtons = new RadioButton[]{
                findViewById(R.id.survey2_personal1),
                findViewById(R.id.survey2_personal2),
                findViewById(R.id.survey2_personal3),
                findViewById(R.id.survey2_personal4)
        };

        // 피부 타입 토글 처리
        for (RadioButton btn : skinButtons) {
            btn.setOnClickListener(v -> {
                if (btn.equals(selectedSkin)) {
                    btn.setChecked(false);
                    selectedSkin = null;
                } else {
                    uncheckAll(skinButtons);
                    btn.setChecked(true);
                    selectedSkin = btn;
                }
            });
        }

        // 퍼스널컬러 토글 처리
        for (RadioButton btn : colorButtons) {
            btn.setOnClickListener(v -> {
                if (btn.equals(selectedColor)) {
                    btn.setChecked(false);
                    selectedColor = null;
                } else {
                    uncheckAll(colorButtons);
                    btn.setChecked(true);
                    selectedColor = btn;
                }
            });
        }
    }

    // 모든 버튼 체크 해제
    private void uncheckAll(RadioButton[] buttons) {
        for (RadioButton btn : buttons) {
            btn.setChecked(false);
        }
    }
}
