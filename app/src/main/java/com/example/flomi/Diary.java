package com.example.flomi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Diary extends AppCompatActivity {

    MultiAutoCompleteTextView answer1, answer2;
    ImageButton back, imageButton;

    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CODE_GALLERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        answer1 = findViewById(R.id.diary_answer1);
        answer2 = findViewById(R.id.diary_answer2);
        imageButton = findViewById(R.id.diary_pick); // 갤러리용 이미지 버튼
        back = findViewById(R.id.backButton);

        // 갤러리 이미지 불러오기
        imageButton.setOnClickListener(view -> checkPermissionAndOpenGallery());

        // 저장 버튼 클릭
        Button save = findViewById(R.id.save);
        save.setOnClickListener(view -> {
            String title = answer1.getText().toString().trim();
            String content = answer2.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty()) {
                DiaryDatabase db = DiaryDatabase.getInstance(getApplicationContext());
                DiaryEntity diary = new DiaryEntity(title, content);

                new Thread(() -> {
                    db.diaryDao().insert(diary);
                    runOnUiThread(() -> {
                        Intent intent = new Intent(Diary.this, DiaryList.class);
                        startActivity(intent);
                    });
                }).start();
            } else {
                Intent intent = new Intent(Diary.this, DiaryList.class);
                startActivity(intent);
            }
        });

        // 뒤로가기
        back.setOnClickListener(view -> {
            Intent intent = new Intent(Diary.this, DiaryList.class);
            startActivity(intent);
        });
    }

    // 권한 확인 및 갤러리 열기
    private void checkPermissionAndOpenGallery() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageButton.setImageURI(selectedImageUri); // 이미지 버튼에 이미지 표시
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }
}