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
import java.util.Date;

public class Detail extends AppCompatActivity {

    private Product product;       // 멤버 변수
    private ProductDao productDao; // 멤버 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // UI 연결
        ImageButton back = findViewById(R.id.backButton);
        TextView tvCompany = findViewById(R.id.company);
        TextView tvProduct = findViewById(R.id.product);
        TextView tvCompany2 = findViewById(R.id.company2);
        TextView tvCompany_Count = findViewById(R.id.com_productCount);
        TextView tvExplain = findViewById(R.id.longtext);
        TextView tvContent = findViewById(R.id.content);
        ImageView ivImage = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        ToggleButton likeToggleButton = findViewById(R.id.like);
        ImageView imagecontent = findViewById(R.id.image_content);

        // DB, DAO 초기화 (멤버 변수에 할당)
        AppDatabase db = AppDatabase.getInstance(this);
        productDao = db.productDao();

        int productId = getIntent().getIntExtra("id", -1);

        if (productId != -1) {

            new Thread(() -> {
                // 멤버 변수 product에 할당
                product = productDao.getProductById(productId);

                runOnUiThread(() -> {
                    if (productId != -1) {
                        new Thread(() -> {
                            product = productDao.getProductById(productId);
                            if (product != null) {
                                // createdAt 업데이트 (예: 현재 시간으로)
                                product.setCreatedAt(new Date());
                                productDao.updateProduct(product);
                            }

                            runOnUiThread(() -> {
                                if (product != null) {
                                    // 기존 UI 업데이트 코드
                                    tvCompany.setText(product.getCompany() + " >");
                                    tvCompany2.setText(product.getCompany());
                                    tvProduct.setText(product.getName());
                                    tvCompany_Count.setText(product.getCompany_productCount());
                                    String combinedText =
                                            "고민: " + product.getConcerns() + "\n"
                                                    + "효능: " + product.getEfficacy()+ "\n"+ "\n"
                                            +"설명: "+product.getContent();
                                    tvExplain.setText(combinedText);
                                    likeToggleButton.setChecked(product.isLiked());

                                    try {
                                        InputStream is = getAssets().open("picture/" + product.getImage());
                                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                                        ivImage.setImageBitmap(bitmap);
                                        is.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        ivImage.setImageResource(R.drawable.light);
                                    }
                                    try {
                                        InputStream is2 = getAssets().open("picture/" + product.getImage2());
                                        Bitmap bitmap2 = BitmapFactory.decodeStream(is2);
                                        imagecontent.setImageBitmap(bitmap2);
                                        is2.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        imagecontent.setImageResource(R.drawable.light);
                                    }
                                }
                            });
                        }).start();
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

        likeToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (product == null) return;

            product.isLiked = isChecked;

            new Thread(() -> {
                productDao.updateProduct(product);
            }).start();
        });
    }
}
