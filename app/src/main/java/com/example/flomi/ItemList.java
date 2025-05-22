package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

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

public class ItemList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton back;
    private ImageButton like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemlist);

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.backButton);
        like = findViewById(R.id.likeButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Room DB에서 데이터 불러오기 (백그라운드 스레드)
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Product> productList = db.productDao().getAllProducts();

            runOnUiThread(() -> {
                CustomAdapter adapter = new CustomAdapter(productList);
                recyclerView.setAdapter(adapter);
            });
        }).start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(ItemList.this, Category.class);
            startActivity(intent);
        });

        like.setOnClickListener(view -> {
            Intent intent = new Intent(ItemList.this, LikeList.class);
            startActivity(intent);
        });
    }
}