package com.example.flomi;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flomi.data.AppDatabase;
import com.example.flomi.data.DiaryEntity;

import java.io.InputStream;

public class Diary extends AppCompatActivity {

    MultiAutoCompleteTextView answer1, answer2;
    ImageButton back, imageButton;

    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_CAMERA_PERMISSION = 101;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    private String selectedImageUriString = null;
    private Uri photoUri = null;

    private long lastClickTime = 0;

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
        imageButton = findViewById(R.id.diary_pick);
        back = findViewById(R.id.backButton);

        // 갤러리 런처
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();

                        Uri copiedImageUri = copyImageToPicturesFolder(selectedImageUri);

                        if (copiedImageUri != null) {
                            selectedImageUriString = copiedImageUri.toString();
                            imageButton.setImageURI(copiedImageUri);
                        } else {
                            Toast.makeText(this, "이미지 저장 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // 카메라 런처
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imageButton.setImageURI(photoUri);
                        selectedImageUriString = photoUri.toString();
                    }
                });

        // 이미지 버튼 클릭 시 선택 다이얼로그
        imageButton.setOnClickListener(view -> {
            if (System.currentTimeMillis() - lastClickTime < 1000) return;
            lastClickTime = System.currentTimeMillis();
            showImageSourceDialog();
        });

        // 저장 버튼
        Button save = findViewById(R.id.save);
        save.setOnClickListener(view -> {
            String title = answer1.getText().toString().trim();
            String content = answer2.getText().toString().trim();

            if (!title.isEmpty() && !content.isEmpty()) {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                DiaryEntity diary = new DiaryEntity(title, content);
                diary.setImageUri(selectedImageUriString);

                new Thread(() -> {
                    db.diaryDao().insert(diary);
                    runOnUiThread(() -> {
                        startActivity(new Intent(Diary.this, DiaryList.class));
                    });
                }).start();
            } else {
                startActivity(new Intent(Diary.this, DiaryList.class));
            }
        });

        // 뒤로가기
        back.setOnClickListener(view -> {
            startActivity(new Intent(Diary.this, DiaryList.class));
        });
    }

    private void showImageSourceDialog() {
        // 카메라 or 갤러리 선택 다이얼로그
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("이미지 선택")
                .setItems(new CharSequence[]{"갤러리에서 선택", "카메라로 촬영"}, (dialog, which) -> {
                    if (which == 0) {
                        checkGalleryPermission();
                    } else {
                        checkCameraPermission();
                    }
                }).show();
    }

    // 갤러리 권한 체크
    private void checkGalleryPermission() {
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE_PERMISSION);
        } else {
            openGallery();
        }
    }

    // 카메라 권한 체크
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            dispatchTakePictureIntent();
        }
    }

    // 갤러리 열기
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    // 카메라 실행
    private void dispatchTakePictureIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver resolver = getContentResolver();
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri != null) {
            photoUri = imageUri;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(intent);
        } else {
            Toast.makeText(this, "갤러리에 저장할 수 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else if (requestCode == REQUEST_CAMERA_PERMISSION &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {
            Toast.makeText(this, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri copyImageToPicturesFolder(Uri sourceUri) {
        try {
            ContentResolver resolver = getContentResolver();

            ContentValues values = new ContentValues();
            String filename = "IMG_" + System.currentTimeMillis() + ".jpg";
            values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Flomi");
            // Flomi 폴더 안에 저장

            Uri newImageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (newImageUri == null) return null;

            // 원본 이미지 열기
            InputStream inputStream = resolver.openInputStream(sourceUri);
            if (inputStream == null) return null;

            // 새 파일에 쓰기
            try (java.io.OutputStream outputStream = resolver.openOutputStream(newImageUri)) {
                if (outputStream == null) return null;

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            inputStream.close();

            return newImageUri;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
