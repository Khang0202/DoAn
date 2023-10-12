package com.doannganh.warningmap.Activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.doannganh.warningmap.Activity.Admin.NewWarningActivity;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Repository.WarningRepository;
import com.doannganh.warningmap.databinding.ActivityCaptureBinding;
import com.doannganh.warningmap.databinding.ActivityNewWarningBinding;

import java.io.File;

public class CaptureActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 102;
    ActivityCaptureBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageUri = createUri();
        StaticClass.imageUri = imageUri;

        registerPictureLauncher();
        binding.btnTakePicture.setOnClickListener(v -> {
            checkCameraPermission();
        });
        binding.btnChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFileFromUriExists(imageUri, getContentResolver()))
                {
                    StaticClass.imageUri = imageUri;
                    StaticClass.tempUrl = new WarningRepository().uploadToCloudinary(CaptureActivity.this);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Take Picture First",Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.btnBack.setOnClickListener(v -> finish());
    }
    public boolean isFileFromUriExists(Uri uri, ContentResolver contentResolver) {
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    String fileName = cursor.getString(nameIndex);
                    return fileName != null && !fileName.isEmpty();
                }
            }
        }
        return false;
    }

    private Uri createUri() {
        File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "com.doannganh.warningmap.fileProvider",
                imageFile
        );
    }
    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        try {
                            if (result) {
                                binding.ivUser.setImageURI(null);
                                binding.ivUser.setImageURI(imageUri);
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }
        );
    }
    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            takePictureLauncher.launch(imageUri);
        } else {
            ActivityCompat.requestPermissions(CaptureActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureLauncher.launch(imageUri);
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}