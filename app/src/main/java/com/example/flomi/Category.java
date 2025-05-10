package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category extends AppCompatActivity {

    private ListView listView1, listView2;
    private ArrayAdapter<String> categoryAdapter, detailAdapter;

    // 카테고리 리스트
    private List<String> categories = Arrays.asList(
            "스킨케어", "클렌징", "마스크", "선케어","메이크업", "기타"
    );

    // 카테고리에 따른 상세 항목 매핑
    private Map<String, List<String>> detailMap = new HashMap<String, List<String>>() {{
        put("스킨케어", Arrays.asList("스킨/토너","로션/에멀젼", "에센스/앰플/세럼","페이스오일", "크림","아이케어","미스트","젤", "스킨/토너 패드","밤/멀티밤"));
        put("클렌징", Arrays.asList("클렌징오일", "폼클렌저", "클렌징워터"));
        put("마스크", Arrays.asList("시트마스크", "슬리핑팩", "워시오프"));
        put("선케어", Arrays.asList("선크림", "선스틱", "선스프레이"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        // 시스템 바 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 리스트뷰 연결
        listView1 = findViewById(R.id.list_view);
        listView2 = findViewById(R.id.list_view2);

        // 카테고리 어댑터
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        listView1.setAdapter(categoryAdapter);

        // 초기 상세 항목 표시
        updateDetailList("스킨케어");

        // 클릭 시 상세 항목 변경
        listView1.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories.get(position);
            updateDetailList(selectedCategory);
        });


        // 홈 버튼
        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, Home.class);
            startActivity(intent);
        });

        // 다이어리 버튼
        ImageButton diary = findViewById(R.id.diary);
        diary.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, Diary.class);
            startActivity(intent);
        });


        // 리스트뷰2 클릭 시 List화면으로 이동
        listView2.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);

            // 카테고리명에 따라 파일 이름 결정
            int rawId = 0;
            switch (selectedCategory) {
                case "스킨케어":
                    rawId = R.raw.skincare;
                    break;

            }

            Intent intent = new Intent(Category.this, ItemList.class);
            intent.putExtra("rawId", rawId);
            startActivity(intent);
        });
    }

    // 상세 항목 리스트뷰 업데이트
    private void updateDetailList(String category) {
        List<String> details = detailMap.get(category);
        if (details != null) {
            detailAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, details);
            listView2.setAdapter(detailAdapter);
        }
    }


}
