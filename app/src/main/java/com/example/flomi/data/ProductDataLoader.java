package com.example.flomi.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.RawRes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ProductDataLoader {

    public static void loadAndStoreProducts(Context context, @RawRes int rawResId) {
        new Thread(() -> {
            try {
                // JSON 읽기
                Resources res = context.getResources();
                InputStream inputStream = res.openRawResource(rawResId);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                reader.close();

                String json = jsonBuilder.toString();

                // JSON 해시 계산 (간단히 MD5 사용)
                String newJsonHash = md5(json);

                // 이전 해시 불러오기 (SharedPreferences 사용)
                SharedPreferences prefs = context.getSharedPreferences("product_prefs", Context.MODE_PRIVATE);
                String savedHash = prefs.getString("json_hash", "");

                if (!newJsonHash.equals(savedHash)) {
                    // 해시 다르면 데이터 변경된 것 → DB 업데이트 필요

                    // JSON 파싱
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Product>>() {
                    }.getType();
                    List<Product> products = gson.fromJson(json, listType);

                    AppDatabase db = AppDatabase.getInstance(context);

                    // 트랜잭션으로 기존 데이터 삭제 후 새로 삽입
                    db.runInTransaction(() -> {
                        db.productDao().deleteAll();
                        db.productDao().insertAll(products);
                    });

                    // 변경된 해시 저장
                    prefs.edit().putString("json_hash", newJsonHash).apply();
                }
                // else 기존 데이터와 같아서 업데이트 불필요

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // MD5 해시 계산 함수
    private static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}