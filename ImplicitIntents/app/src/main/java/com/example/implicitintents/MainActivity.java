package com.example.implicitintents;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MyLog";

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
}