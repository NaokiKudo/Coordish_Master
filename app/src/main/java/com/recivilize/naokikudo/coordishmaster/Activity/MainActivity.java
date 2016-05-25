package com.recivilize.naokikudo.coordishmaster.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.recivilize.naokikudo.coordishmaster.Position.GPS;
import com.recivilize.naokikudo.coordishmaster.R;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    TextView textView;
    GPS Gps;
    public static final int REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void get(View view) {
        if (Gps == null) {
            Gps = new GPS(this);
        }
        if (Gps.getLatitude() == 0.0 || Gps.getLongitude() == 0.0) {
            if (!GPS.checkPermission(this)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Gps.startGPS();
            }
        }
        if (Gps.getLatitude() != 0.0 || Gps.getLongitude() != 0.0) {
            Snackbar.make(view, "位置情報の取得が完了しました", Snackbar.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Gps.startGPS();
                } else {
                    Snackbar.make(textView, "Location access is required to get your position", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Request the permission
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_LOCATION);
                                }
                            }).show();
                }
                return;
            }

        }
    }

}
