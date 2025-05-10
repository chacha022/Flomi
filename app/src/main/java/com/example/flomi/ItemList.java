package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

public class ItemList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mList;
    private SimpleStringAdapter mAdapter;

    private ArrayList<String> loadListFromRaw(int rawResId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(rawResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONArray jsonArray = new JSONArray(builder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }

            Log.d("DEBUG", "JSON loaded successfully. Items: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemlist);

        mRecyclerView = findViewById(R.id.recyclerView);

        int rawId = getIntent().getIntExtra("rawId", -1);
        if (rawId != -1) {
            mList = loadListFromRaw(rawId);
        } else {
            mList = new ArrayList<>();
        }

        mAdapter = new SimpleStringAdapter(mList);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        mRecyclerView.setAdapter(mAdapter);

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

//        Spinner testSpinner = (Spinner) findViewById(R.id.spinner);
//
//        String[] kinds1 = getResources().getStringArray(R.array.item_list);
//        // List<String> kinds2 = Arrays.asList(getResources().getStringArray(R.array.item_list));
//
//        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),R.layout.activity_itemlist,kinds1);
//        adapter.setDropDownViewResource(R.layout.activity_itemlist);
//        testSpinner.setAdapter(adapter);


        Toast.makeText(this, "데이터 개수: " + mList.size(), Toast.LENGTH_SHORT).show();

    }
}