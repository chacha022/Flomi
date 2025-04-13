package com.example.flomi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton next = findViewById(R.id.survey3_btn_next1);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Survey3.this, Survey4.class);
                startActivity(intent);
            }
        });

        ImageButton back = findViewById(R.id.survey3_backButton);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent = new Intent(Survey3.this, Survey2.class);
                startActivity(intent);
            }
        });
    }
}