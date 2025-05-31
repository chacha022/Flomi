package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Survey2 extends AppCompatActivity {

    private String selectedSkinType = "";
    private String selectedColor = "";
    private int lastSkinTypeId = -1;
    private int lastColorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        RadioGroup skinTypeGroup = findViewById(R.id.radioGroup_skinType);
        RadioGroup colorGroup = findViewById(R.id.radioGroup_color);

        Intent received = getIntent();
        selectedSkinType = received.getStringExtra("skinType") != null ? received.getStringExtra("skinType") : "";
        selectedColor = received.getStringExtra("personalColor") != null ? received.getStringExtra("personalColor") : "";

        // 라디오버튼 개별 처리
        setupToggleRadioButtons(skinTypeGroup, true);
        setupToggleRadioButtons(colorGroup, false);

        ImageButton next = findViewById(R.id.survey2_btn_next1);
        next.setOnClickListener(view -> {
            if (selectedSkinType.isEmpty()) {
                Toast.makeText(Survey2.this, "피부 타입을 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedColor.isEmpty()) {
                Toast.makeText(Survey2.this, "퍼스널 컬러를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Survey2.this, Survey3.class);
            intent.putExtra("gender", received.getStringExtra("gender"));
            intent.putExtra("birthYear", received.getIntExtra("birthYear", 0));
            intent.putExtra("skinType", selectedSkinType);
            intent.putExtra("personalColor", selectedColor);
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.survey2_backButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Survey2.this, Survey1.class);
            intent.putExtra("gender", received.getStringExtra("gender"));
            intent.putExtra("birthYear", received.getIntExtra("birthYear", 0));
            startActivity(intent);
        });
    }

    private void setupToggleRadioButtons(RadioGroup group, boolean isSkinGroup) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);

            if (child instanceof ViewGroup) {
                ViewGroup nested = (ViewGroup) child;
                for (int j = 0; j < nested.getChildCount(); j++) {
                    View btn = nested.getChildAt(j);
                    if (btn instanceof RadioButton) {
                        setupButtonLogic((RadioButton) btn, isSkinGroup);
                    }
                }
            } else if (child instanceof RadioButton) {
                setupButtonLogic((RadioButton) child, isSkinGroup);
            }
        }
    }

    private void setupButtonLogic(RadioButton button, boolean isSkinGroup) {
        button.setOnClickListener(view -> {
            int id = button.getId();
            boolean isChecked = button.isChecked();

            if (isSkinGroup) {
                if (id == lastSkinTypeId) {
                    button.setChecked(false);
                    selectedSkinType = "";
                    lastSkinTypeId = -1;
                } else {
                    selectedSkinType = button.getText().toString();
                    lastSkinTypeId = id;
                }
            } else {
                if (id == lastColorId) {
                    button.setChecked(false);
                    selectedColor = "";
                    lastColorId = -1;
                } else {
                    selectedColor = button.getText().toString();
                    lastColorId = id;
                }
            }
        });

        // 초기 복원
        if (isSkinGroup && button.getText().toString().equals(selectedSkinType)) {
            button.setChecked(true);
            lastSkinTypeId = button.getId();
        } else if (!isSkinGroup && button.getText().toString().equals(selectedColor)) {
            button.setChecked(true);
            lastColorId = button.getId();
        }
    }
}
