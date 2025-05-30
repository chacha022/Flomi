package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.Product;

import java.util.List;

public class SearchList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 검색어 받아오기
        String keyword = getIntent().getStringExtra("search_keyword");

        // DB에서 검색
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "flome-db")
                .allowMainThreadQueries()
                .build();

        List<Product> result = db.productDao().searchByNameOrCompany(keyword);

        // 어댑터 연결
        adapter = new CustomAdapter(result);
        recyclerView.setAdapter(adapter);

        ImageButton back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(SearchList.this, Home.class);
                startActivity(intent);
            }
        });
    }

}
