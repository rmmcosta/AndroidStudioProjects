package com.example.showmylocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final String LOG = "MyLog";
    private static LocationManager locationManager;
    private TextView tvLat;
    private TextView tvLong;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLat = findViewById(R.id.tvLat);
        tvLong = findViewById(R.id.tvLong);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkAndRequestPermission();
    }

    private void checkAndRequestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(LOG, "no permission");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LOG, "on permission result");
        if(requestCode==1)
        {
            for (String s : permissions) {
                Log.d(LOG, s);
            }
            for (int i : grantResults) {
                Log.d(LOG, String.valueOf(i));
            }
        }
        checkAndRequestPermission();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG, "on location changed");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        tvLat.setText(String.valueOf(latitude));
        tvLong.setText(String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG, "on status changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG, "on provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(LOG, "on provider disabled");
    }

    public void showMap(View view) {
        Uri uri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void updateLocation(View view) {
        checkAndRequestPermission();
    }
}