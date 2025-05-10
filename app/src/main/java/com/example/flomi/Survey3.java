package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Survey3 extends AppCompatActivity {

    private RadioGroup radioGroup;
    private int lastCheckedId = -1;  // 마지막으로 선택된 버튼 ID 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey3);

        ImageButton next = findViewById(R.id.survey3_btn_next1);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(Survey3.this, Survey4.class);
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.survey3_backButton);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Survey3.this, Survey2.class);
            startActivity(intent);
        });

        // 라디오 그룹 처리
        radioGroup = findViewById(R.id.radioGroup);

        // 각 라디오 버튼을 찾아 클릭 리스너 지정
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RelativeLayout) {
                RelativeLayout layout = (RelativeLayout) child;
                for (int j = 0; j < layout.getChildCount(); j++) {
                    View innerChild = layout.getChildAt(j);
                    if (innerChild instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) innerChild;

                        radioButton.setOnClickListener(v -> {
                            int id = radioButton.getId();
                            if (id == lastCheckedId) {
                                radioGroup.clearCheck(); // 같은 버튼 누르면 취소
                                lastCheckedId = -1;
                            } else {
                                radioGroup.check(id); // 다른 버튼 누르면 선택
                                lastCheckedId = id;
                            }
                        });
                    }
                }
            }
        }
    }
}
