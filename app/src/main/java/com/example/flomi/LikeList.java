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
import java.util.concurrent.Executors;

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

        // Intent에서 값 받아오기
        isLiked = getIntent().getBooleanExtra("isLiked", false);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getInstance(this);

        if (isLiked) {
            showLikedItems();  // 메서드 호출
        } else {
            findViewById(R.id.emptyText).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화면 전환
                Intent intent = new Intent(LikeList.this, ItemList.class);
                startActivity(intent);
            }
        });
    }
    // onCreate 밖에 별도로 메서드 정의
    private void showLikedItems() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Product> likedProducts = db.productDao().getLikedProducts();

            runOnUiThread(() -> {
                if (likedProducts == null || likedProducts.isEmpty()) {
                    findViewById(R.id.emptyText).setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    adapter = new CustomAdapter(likedProducts);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    findViewById(R.id.emptyText).setVisibility(View.GONE);
                }
            });
        });
    }
}

