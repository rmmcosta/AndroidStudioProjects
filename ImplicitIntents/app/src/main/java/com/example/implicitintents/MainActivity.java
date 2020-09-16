package com.example.implicitintents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CODE = 1, CAMERA_REQUEST_CODE = 2;
    private static final String LOG = "MyLog";
    private static boolean hasCameraPermissions;
    private static boolean is2StartCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void shareText(View view) {
        EditText etText2Share = findViewById(R.id.e_etText2Share);
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle(R.string.share_chooser_title)
                .setText(etText2Share.getText().toString())
                .startChooser();
    }

    public void openLocation(View view) {
        EditText etLocation = findViewById(R.id.c_etLocation);
        Uri uri = Uri.parse("geo:0,0?q=" + etLocation.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG, "can't resolve open location");
            Toast popupMessage = Toast.makeText(getApplicationContext(), R.string.error_popup_location, Toast.LENGTH_SHORT);
            setErrorToast(popupMessage);
            popupMessage.show();
        }
    }

    private void setErrorToast(Toast popupMessage) {
        View popupView = popupMessage.getView();
        popupView.setBackgroundColor(Color.RED);
    }

    public void openWebsite(View view) {
        EditText etWebsite = findViewById(R.id.a_etWebsite);
        Uri uri = Uri.parse(etWebsite.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG, "can't resolve open website");
            Toast popupMessage = Toast.makeText(getApplicationContext(), R.string.error_popup_website, Toast.LENGTH_SHORT);
            setErrorToast(popupMessage);
            popupMessage.show();
        }
    }

    public void takePicture(View view) {
        is2StartCamera = true;
        checkAndRequestCameraPermission();
        if (hasCameraPermissions)
            startCamera();
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE)
            checkGrantedCameraPermissions(permissions, grantResults);
        is2LaunchCamera();
    }

    private void is2LaunchCamera() {
        Log.d(LOG, "is2StartCamera:" + is2StartCamera + ", hasCameraPermissions:" + hasCameraPermissions);
        if (is2StartCamera && hasCameraPermissions) {
            startCamera();
        }
        is2StartCamera = false;
    }

    private void checkGrantedCameraPermissions(String[] permissions, int[] grantResults) {
        Log.d(LOG, "checkGrantedCameraPermissions");
        for (String s : permissions) {
            Log.d(LOG, s);
            if (s.equalsIgnoreCase("android.permission.CAMERA")) {
                hasCameraPermissions = checkGrantedResults(grantResults);
            }
        }
        Log.d(LOG, "hasCameraPermissions=" + hasCameraPermissions);
    }

    private boolean checkGrantedResults(int[] grantResults) {
        Log.d(LOG, "checkGrantedResults");
        for (int i : grantResults)
            if (i == 0)
                return true;
        return false;
    }

    private void checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            hasCameraPermissions = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CODE) {
            Intent redirectIntent = new Intent(this, ImageActivity.class);
            redirectIntent.putExtras(data.getExtras());
            startActivity(redirectIntent);
        }
    }
}