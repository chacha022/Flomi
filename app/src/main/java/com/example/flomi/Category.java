package com.example.flomi;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.CategoryEntity;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Category extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private AppDatabase db;
    private Uri photoURI;
    private CheckBox check1, check2, check3, check4, check5, check6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        // 시스템 바 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        // 체크박스 초기화
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);
        check6 = findViewById(R.id.check6);

        // 찾기 버튼
        Button find = findViewById(R.id.find);
        find.setOnClickListener(view -> {
            String checkedStr = getCheckedConditions();
            if (checkedStr.isEmpty()) {
                Toast.makeText(this, "하나 이상의 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
                return;  // 아무 체크도 안 했으면 중단
            }

            // DB 저장
            CategoryEntity categoryEntity = new CategoryEntity(checkedStr);
            new Thread(() -> db.categoryDao().insert(categoryEntity)).start();

            // ItemList로 이동
            Intent intent = new Intent(Category.this, ItemList.class);
            startActivity(intent);
        });


        // 홈 버튼
        ImageButton home = findViewById(R.id.home);
        home.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, Home.class);
            startActivity(intent);
        });

        // 다이어리 버튼
        ImageButton diary = findViewById(R.id.diary);
        diary.setOnClickListener(view -> {
            Intent intent = new Intent(Category.this, DiaryList.class);
            startActivity(intent);
        });

        // 카메라 버튼
        ImageButton cameraBtn = findViewById(R.id.camera);
        cameraBtn.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                dispatchTakePictureIntent(); // 카메라 실행
            }
        });

        // 카메라 촬영 완료 후 저장 코드
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "사진 촬영 완료", Toast.LENGTH_SHORT).show();

                        ((ImageButton) findViewById(R.id.camera)).setImageURI(photoURI);

                    }
                }
        );


    }


    private String getCheckedConditions() {
        StringBuilder sb = new StringBuilder();
        if (check1.isChecked()) sb.append(check1.getText().toString()).append(",");
        if (check2.isChecked()) sb.append(check2.getText().toString()).append(",");
        if (check3.isChecked()) sb.append(check3.getText().toString()).append(",");
        if (check4.isChecked()) sb.append(check4.getText().toString()).append(",");
        if (check5.isChecked()) sb.append(check5.getText().toString()).append(",");
        if (check6.isChecked()) sb.append(check6.getText().toString()).append(",");

        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void dispatchTakePictureIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);  // 갤러리 Pictures 폴더

        ContentResolver resolver = getContentResolver();
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri != null) {
            photoURI = imageUri;

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            cameraLauncher.launch(intent);
        } else {
            Toast.makeText(this, "갤러리에 저장할 수 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
