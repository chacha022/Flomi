package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class Survey3 extends AppCompatActivity {

    private Set<String> selectedConcerns = new HashSet<>(); // 다중 선택 저장용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);

        ImageButton next = findViewById(R.id.survey3_btn_next1);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(Survey3.this, Survey4.class);
            intent.putExtra("gender", getIntent().getStringExtra("gender"));
            intent.putExtra("birthYear", getIntent().getIntExtra("birthYear", 0));
            intent.putExtra("skinType", getIntent().getStringExtra("skinType"));
            intent.putExtra("personalColor", getIntent().getStringExtra("personalColor"));
            intent.putExtra("skinConcern", TextUtils.join(",", selectedConcerns)); // 다중 선택된 값들을 문자열로 변환하여 전달
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.survey3_backButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Survey3.this, Survey2.class);
            intent.putExtra("gender", getIntent().getStringExtra("gender"));
            intent.putExtra("birthYear", getIntent().getIntExtra("birthYear", 0));
            intent.putExtra("skinType", getIntent().getStringExtra("skinType"));
            intent.putExtra("personalColor", getIntent().getStringExtra("personalColor"));
            startActivity(intent);
        });

        // 라디오 그룹에서 모든 버튼 가져와서 클릭 리스너 설정
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RelativeLayout) {
                RelativeLayout layout = (RelativeLayout) child;
                for (int j = 0; j < layout.getChildCount(); j++) {
                    View innerChild = layout.getChildAt(j);
                    if (innerChild instanceof RadioButton) {
                        RadioButton rb = (RadioButton) innerChild;

                        // 선택 상태 유지하면서 토글 처리
                        rb.setOnClickListener(v -> {
                            String value = rb.getText().toString();
                            if (rb.isSelected()) {
                                rb.setSelected(false);
                                rb.setChecked(false);
                                selectedConcerns.remove(value);
                            } else {
                                rb.setSelected(true);
                                rb.setChecked(true);
                                selectedConcerns.add(value);
                            }
                        });
                    }
                }
            }
        }
    }
}
