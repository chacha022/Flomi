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
                // 1. JSON 읽기
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
                String newJsonHash = md5(json);

                AppDatabase db = AppDatabase.getInstance(context);
                int productCount = db.productDao().getCount(); // 👉 현재 DB에 저장된 제품 수 확인


                // 2. SharedPreferences에서 이전 상태 확인
                SharedPreferences prefs = context.getSharedPreferences("product_prefs", Context.MODE_PRIVATE);
                String savedHash = prefs.getString("json_hash", "");
                int savedSchemaVersion = prefs.getInt("schema_version", -1);

                int currentSchemaVersion = AppDatabase.VERSION;

                boolean shouldReload = false;

                if (!newJsonHash.equals(savedHash)) {
                    shouldReload = true;
                } else if (savedSchemaVersion != currentSchemaVersion) {
                    shouldReload = true;
                }else if (productCount == 0) { // 👉 DB가 비어있다면 로드
                    shouldReload = true;
                }


                if (shouldReload) {
                    // 3. JSON -> 객체로 변환
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Product>>() {}.getType();
                    List<Product> products = gson.fromJson(json, listType);

                    // 4. Room DB에 저장
                    db.runInTransaction(() -> {
                        db.productDao().deleteAll();
                        db.productDao().insertAll(products);
                    });

                    // 5. SharedPreferences에 최신 상태 저장
                    prefs.edit()
                            .putString("json_hash", newJsonHash)
                            .putInt("schema_version", currentSchemaVersion)
                            .apply();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // MD5 해시 생성기
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
