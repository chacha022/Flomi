package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // 상태바/네비바에 대응한 패딩 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI 뷰 연결
        ImageButton back = findViewById(R.id.backButton);
        Button like = findViewById(R.id.like);
        TextView tvCompany = findViewById(R.id.company);
        TextView tvProduct = findViewById(R.id.product);
        TextView tvCompany2 = findViewById(R.id.company2);
        ImageView ivImage = findViewById(R.id.image);

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        String company = intent.getStringExtra("company");
        String name = intent.getStringExtra("name");
        String efficacy1 = intent.getStringExtra("efficacy1");
        int imageResId = intent.getIntExtra("image", R.drawable.light); // 기본 이미지 지정

        // 데이터 화면에 세팅
        tvCompany.setText(company + " >");
        tvCompany2.setText(company);
        tvProduct.setText(name);
        // 만약 효능 관련 TextView도 있다면 여기에 세팅 가능
        // 예) tvEfficacy.setText(efficacy1);


        ivImage.setImageResource(imageResId);


        // 뒤로가기 버튼 클릭 시 ItemList로 이동
        back.setOnClickListener(view -> {
            Intent backIntent = new Intent(Detail.this, ItemList.class);
            startActivity(backIntent);
            finish();
        });


        // 홈 버튼
        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent h = new Intent(Detail.this, Home.class);
            startActivity(h);
        });


        Button likeButton = findViewById(R.id.like);
        likeButton.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
        });
        // TODO: like 버튼 기능 구현 (찜하기 등)
    }
}