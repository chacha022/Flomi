package com.example.flomi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner testSpinner = (Spinner) findViewById(R.id.spinner);

        String[] kinds1 = getResources().getStringArray(R.array.item_list);
        // List<String> kinds2 = Arrays.asList(getResources().getStringArray(R.array.item_list));

        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),R.layout.activity_itemlist,kinds1);
        adapter.setDropDownViewResource(R.layout.activity_itemlist);
        testSpinner.setAdapter(adapter);

    }
}