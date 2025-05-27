package com.example.flomi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.io.InputStream;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // 상태바/네비바 대응 패딩 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI 연결
        ImageButton back = findViewById(R.id.backButton);
        Button like = findViewById(R.id.like);
        TextView tvCompany = findViewById(R.id.company);
        TextView tvProduct = findViewById(R.id.product);
        TextView tvCompany2 = findViewById(R.id.company2);
        ImageView ivImage = findViewById(R.id.imageView);

        // Intent 데이터 수신
        Intent intent = getIntent();
        String company = intent.getStringExtra("company");
        String name = intent.getStringExtra("name");
        String efficacy1 = intent.getStringExtra("efficacy");
        String imageFileName = intent.getStringExtra("image");  // 예: "sample1.png"

        // 텍스트 표시
        tvCompany.setText(company + " >");
        tvCompany2.setText(company);
        tvProduct.setText(name);
        // 효능 텍스트도 필요하면 여기에 추가 가능

        // 이미지 표시 (assets/picture/ 경로에서 Bitmap 로드)
        try {
            InputStream is = getAssets().open("picture/" + imageFileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ivImage.setImageBitmap(bitmap);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            ivImage.setImageResource(R.drawable.light);  // 오류 시 기본 이미지
        }

        // 뒤로 가기
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

        // 찜하기 (like) 버튼 클릭 처리
        Button likeButton = findViewById(R.id.like);
        likeButton.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
        });
        // TODO: 찜 목록 저장 기능 구현
    }
}
