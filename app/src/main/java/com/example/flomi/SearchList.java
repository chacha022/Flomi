package com.example.flomi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.Product;

import java.util.List;

public class SearchList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.emptyView); // 결과 없을 때 표시할 TextView (레이아웃에 추가 필요)

        // 검색어 받아오기
        String keyword = getIntent().getStringExtra("search_keyword");

        // DB 비동기 접근
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<Product> result = db.productDao().searchByNameOrCompany(keyword);

            runOnUiThread(() -> {
                if (result.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("검색 결과가 없습니다.");
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapter = new CustomAdapter(result);
                    recyclerView.setAdapter(adapter);
                }
            });
        }).start();

        // 뒤로가기 버튼 처리
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(view -> finish());
    }
}
