package com.example.flomi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.Product;
import com.example.flomi.data.ProductDao;

import java.io.IOException;
import java.io.InputStream;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // UI 연결
        ImageButton back = findViewById(R.id.backButton);
        Button like = findViewById(R.id.like);
        TextView tvCompany = findViewById(R.id.company);
        TextView tvProduct = findViewById(R.id.product);
        TextView tvCompany2 = findViewById(R.id.company2);
        TextView tvCompany_Count = findViewById(R.id.com_productCount);
        TextView tvExplain = findViewById(R.id.longtext);
        TextView tvContent = findViewById(R.id.content);
        ImageView ivImage = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);

        // ✅ Intent로 id만 전달받음
        int productId = getIntent().getIntExtra("id", -1);

        if (productId != -1) {
            // Room 데이터베이스에서 제품 정보 불러오기
            AppDatabase db = AppDatabase.getInstance(this);
            ProductDao productDao = db.productDao();

            new Thread(() -> {
                Product product = productDao.getProductById(productId);

                runOnUiThread(() -> {
                    if (product != null) {
                        tvCompany.setText(product.getCompany() + " >");
                        tvCompany2.setText(product.getCompany());
                        tvProduct.setText(product.getName());
                        tvContent.setText(product.getContent());
                        tvCompany_Count.setText(product.getCompany_productCount());
                        String combinedText =
                                "고민: " + product.getConcerns() + "\n"
                                + "효능: " + product.getEfficacy();
                        tvExplain.setText(combinedText);

                        // 이미지 로딩 (assets/picture/ 폴더 기준)
                        try {
                            InputStream is = getAssets().open("picture/" + product.getImage());
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            ivImage.setImageBitmap(bitmap);
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            ivImage.setImageResource(R.drawable.light);  // 기본 이미지
                        }
                    }
                });
            }).start();
        }

        // 뒤로가기 버튼
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

        // 찜하기 버튼
        like.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
        });

        // 텍스트 접기/펼치기
        textView.setOnClickListener(new View.OnClickListener() {
            boolean expanded = false;
            @Override
            public void onClick(View v) {
                if (expanded) {
                    tvExplain.setMaxLines(1);
                    textView.setText("더보기");
                } else {
                    tvExplain.setMaxLines(Integer.MAX_VALUE);
                    textView.setText("접기");
                }
                expanded = !expanded;
            }
        });
    }
}
