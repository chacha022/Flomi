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

    public static List<Product> loadProductsFromRaw(Context context, @RawRes int rawResId) {
        try {
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
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            return gson.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
