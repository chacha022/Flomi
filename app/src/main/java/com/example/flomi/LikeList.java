package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.Product;

import java.util.List;

public class LikeList extends AppCompatActivity {

    public boolean isLiked = false; // 이 값을 true로 바꾸면 좋아요 리스트를 표시

    private RecyclerView recyclerView;
    private CustomAdapter adapter; // 사용자 정의 어댑터
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_like_list);

        isLiked = getIntent().getBooleanExtra("isLiked", false); // 인텐트에서 값 받기


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(LikeList.this, ItemList.class);
                startActivity(intent);
            }
        });

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getInstance(this);

        if (isLiked) {
            showLikedItems();
        } else {
            // 예: 빈 화면을 보여주거나 안내 메시지
            TextView emptyText = findViewById(R.id.emptyText);
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void showLikedItems() {
        List<Product> likedProducts = db.productDao().getLikedProducts(); // 좋아요한 제품만 가져오기
        adapter = new CustomAdapter(likedProducts);
        recyclerView.setAdapter(adapter);
    }
}