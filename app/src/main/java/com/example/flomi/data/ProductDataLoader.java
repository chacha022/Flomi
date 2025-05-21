package com.example.flomi.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.RawRes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ProductDataLoader {

    public static void loadAndStoreProducts(Context context, @RawRes int rawResId) {
        new Thread(() -> {
            try {
                // 1. JSON 파일 읽기
                Resources res = context.getResources();
                InputStream inputStream = res.openRawResource(rawResId);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                reader.close();

                // 2. JSON 파싱
                String json = jsonBuilder.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Product>>() {}.getType();
                List<Product> products = gson.fromJson(json, listType);

                // 3. DB 저장 (이미 데이터가 있을 경우 생략)
                AppDatabase db = AppDatabase.getInstance(context);
                List<Product> existing = db.productDao().getAllProducts();

                if (existing.isEmpty()) {
                    db.productDao().insertAll(products);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); // 백그라운드 스레드에서 실행
    }
}