package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemlist);

        //===== 테스트용 더미 데이터 생성 ============================
        ArrayList<Product> productList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            productList.add(new Product(
                    R.drawable.light, // 실제 drawable 이미지로 교체
                    "회사 " + i,
                    "제품명 " + i,
                    "효능 A" + i,
                    "효능 B" + i,
                    "효능 C" + i
            ));
        }

        //===========================================================

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //--- LayoutManager 설정 ------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //--- 어댑터 설정 --------------------------------------------
        CustomAdapter customAdapter = new CustomAdapter(productList);
        recyclerView.setAdapter(customAdapter);

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
                Intent intent = new Intent(ItemList.this, Category.class);
                startActivity(intent);
            }
        });

    }

}



